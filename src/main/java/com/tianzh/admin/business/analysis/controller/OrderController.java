package com.tianzh.admin.business.analysis.controller;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.*;
import com.tianzh.admin.business.analysis.service.*;
import com.tianzh.admin.business.analysis.utils.DateUtils;
import com.tianzh.admin.common.page.PageInfo;
import com.tianzh.admin.common.page.PageUtils;
import com.tianzh.admin.common.util.encrypt.md5.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.omg.CORBA.ORB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by pig on 2015-06-30.
 */
@Controller
public class OrderController {

    final String LETUPAY = "1";
    final String YUANLANGPAY = "2";
    final String ZHANGPAY = "3";
    final String ZHUQUEPAY = "4";

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductIdentificationService productIdentificationService;

    @Autowired
    ProductDetialService productDetialService;

    @Autowired
    private ThPayService thPayService;


    //易接支付回调
    @RequestMapping("yjnotify.do")
    @ResponseBody
    public String addOrder(@RequestParam(value = "app", required = false, defaultValue = "") String app,
                           @RequestParam(value = "channel", required = false) String channel,
                           @RequestParam(value = "revenue", required = false) Integer revenue,
                           @RequestParam(value = "date", required = false) String date) {
        Order order = new Order();

        try {
            //省略验证签名过程
            order.setAmounts(revenue / 100);
            order.setProdIdentification(channel);
            order.setProductId(Integer.parseInt(CacheContainer.getProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + app)));
            order.setSyncDate(date);

            orderService.updateOrder(order);
            orderService.updateCpOrder(order);

        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.debug("updateOrder:{} order exception:{}", order, ExceptionUtils.getMessage(e));
        }
        return "SUCCESS";
    }

    //朱雀支付回调
    @RequestMapping("zhuquenotify.do")
    @ResponseBody
    public String addOrder(@RequestParam(value = "order_id", required = false, defaultValue = "") String order_id,
                           @RequestParam(value = "cpparam", required = false) String cpparam,
                           @RequestParam(value = "report_type", required = false) String reportType,
                           @RequestParam(value = "imei", required = false) String imei,
                           @RequestParam(value = "imsi", required = false) String imsi,
                           @RequestParam(value = "pay_price", required = false) String pay_price,
                           @RequestParam(value = "agent_number", required = false) String agent_number) {
        pay_price = pay_price.substring(0, pay_price.lastIndexOf(".") == -1 ? pay_price.length() : pay_price.lastIndexOf("."));

        if (cpparam.contains("|")) {
            Order order = new Order();

            try {

                //省略验证签名过程
                order.setLinkId(order_id);
                int provider = reportType.equals("5") ? Constant.Provider.CHINA_UNICOM : reportType.equals("6") ? Constant.Provider.CHINA_TELECOM : Constant.Provider.CHINA_MOBILE;
                order.setPayType(ZHUQUEPAY + "_" + provider);
                String[] strings = cpparam.split("\\|");
                Integer productId = Integer.parseInt(CacheContainer.getProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + strings[1]));
                order.setProductId(productId);
                order.setProductName(CacheContainer.getProductName(Constant.ReferenceKey.PRODUCTKEY + productId));
                order.setThirdPartyKey(strings[1]);

                if (strings[1].equals("dtyhf")) {
                    order.setUserId(Integer.parseInt(CacheContainer.getUserId(Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + "|" + strings[1] + "|" + strings[2])));
                    order.setUserCompany(CacheContainer.getUserCompanyName(Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + "|" + strings[1] + "|" + strings[2]));
                    order.setProdIdentification("|" + strings[1] + "|" + strings[2]);

                } else {
                    order.setUserId(Integer.parseInt(CacheContainer.getUserId(Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + strings[2])));
                    order.setUserCompany(CacheContainer.getUserCompanyName(Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + strings[2]));
                    order.setProdIdentification(strings[2]);

                }


                order.setUserToken(imsi);
                order.setAmounts(Integer.parseInt(pay_price) * 100);
                order.setStatus(Constant.OrderStatus.ORDERSUCESS);
                order.setCreateTime(Calendar.getInstance().getTime());
//                order.setCreateTime(DateUtils.formatStr(DateUtils.specifyDate(1)));

                orderService.addOrder(order);
            } catch (Exception e) {
                e.printStackTrace();
                Log.exception.error("addOrder:{} order exception:{}", order, ExceptionUtils.getMessage(e));
            }
        } else {
            String tpk = cpparam.split("_")[0];
            String orderId = cpparam.split("_")[1];
            int chargeOrderId = Integer.parseInt(orderId, 16);
            ThPayOrder thOrder = orderService.findThOrder(chargeOrderId);

            Order order = new Order();
            try {
                //省略验证签名过程
                String userToken = thOrder.getUserToken();
                Date tokenRegTime = thOrder.getTokenRegTime();
                String channelId = thOrder.getChannelId();
                Date orderCreateTime = thOrder.getCreateTime();

                order.setChargeOrderId(chargeOrderId);
                order.setLinkId(order_id);
                int provider = reportType.equals("5") ? Constant.Provider.CHINA_UNICOM : reportType.equals("6") ? Constant.Provider.CHINA_TELECOM : Constant.Provider.CHINA_MOBILE;
                order.setPayType(ZHUQUEPAY + "_" + provider);

                Integer productId = Integer.parseInt(CacheContainer.getProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + tpk));
                order.setProductId(productId);
                order.setProductName(CacheContainer.getProductName(Constant.ReferenceKey.PRODUCTKEY + productId));
                order.setThirdPartyKey(tpk);

                order.setUserId(Integer.parseInt(CacheContainer.getUserId(Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + channelId)));
                order.setUserCompany(CacheContainer.getUserCompanyName(Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + channelId));
                order.setProdIdentification(channelId);

                order.setUserToken(userToken);
                order.setAmounts(Integer.parseInt(pay_price) * 100);
                order.setStatus(Constant.OrderStatus.ORDERSUCESS);
                order.setRegisterTime(tokenRegTime);
                order.setCreateTime(orderCreateTime);
//                order.setCreateTime(DateUtils.formatStr(DateUtils.specifyDate(1)));

                orderService.addOrder(order);

                HashMap<String, Object> updateParams = new HashMap<String, Object>();

                updateParams.put("id", chargeOrderId);
                updateParams.put("amounts", Integer.parseInt(pay_price) * 100);
                orderService.updateChargeOrder(updateParams);
            } catch (Exception e) {
                e.printStackTrace();
                Log.exception.debug("addOrder:{} order exception:{}", order, ExceptionUtils.getMessage(e));
            }
        }
        return "1";
    }

    //掌支付回调
    @RequestMapping("zhangnotify.do")
    @ResponseBody
    public String addOrder(@RequestParam(value = "orderid", required = false, defaultValue = "") String orderid,
                           @RequestParam(value = "transeid", required = false) String transeid,
                           @RequestParam(value = "cporderid", required = false) String cporderid,
                           @RequestParam(value = "feemode", required = false) String feemode,
                           @RequestParam(value = "appId", required = false) String appId,
                           @RequestParam(value = "qdname", required = false) String qdname,
                           @RequestParam(value = "op", required = false) Integer op,
                           @RequestParam(value = "imsi", required = false) String imsi,
                           @RequestParam(value = "paidfee", required = false) Integer paidfee,
                           @RequestParam(value = "pushtype", required = false) Integer pushtype) {
        int chargeOrderId = Integer.parseInt(cporderid, 16);
        ThPayOrder thOrder = orderService.findThOrder(chargeOrderId);

        Order order = new Order();
        try {
            //省略验证签名过程
            String userToken = thOrder.getUserToken();
            Date tokenRegTime = thOrder.getTokenRegTime();
            Date orderCreateTime = thOrder.getCreateTime();

            order.setChargeOrderId(chargeOrderId);
            order.setLinkId(Md5Utils.getMD5((orderid + transeid).getBytes()));
            int provider = op == 2 ? Constant.Provider.CHINA_UNICOM : op == 3 ? Constant.Provider.CHINA_TELECOM : Constant.Provider.CHINA_MOBILE;
            order.setPayType(ZHANGPAY + "_" + provider);

            Integer productId = Integer.parseInt(CacheContainer.getProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + appId));
            order.setProductId(productId);
            order.setProductName(CacheContainer.getProductName(Constant.ReferenceKey.PRODUCTKEY + productId));
            order.setThirdPartyKey(appId);
            order.setProdIdentification(qdname);
            order.setUserId(Integer.parseInt(CacheContainer.getUserId(Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + qdname)));
            order.setUserCompany(CacheContainer.getUserCompanyName(Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + qdname));
            order.setUserToken(StringUtils.isEmpty(userToken) ? "" : userToken);
            order.setAmounts(paidfee);
            order.setStatus(Constant.OrderStatus.ORDERSUCESS);
            order.setRegisterTime(tokenRegTime);
            order.setCreateTime(orderCreateTime);

            orderService.addOrder(order);

            HashMap<String, Object> updateParams = new HashMap<String, Object>();

            updateParams.put("id", chargeOrderId);
            updateParams.put("amounts", paidfee);
            orderService.updateChargeOrder(updateParams);
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.debug("addOrder:{} order exception:{}", order, ExceptionUtils.getMessage(e));
        }

        return "0";
    }

    //元朗支付回调
    @RequestMapping("ylnotify.do")
    @ResponseBody
    public String addOrder(@RequestParam(value = "account", required = false, defaultValue = "") String account,
                           @RequestParam(value = "orderno", required = false) String orderno,
                           @RequestParam(value = "userOrderId", required = false) String userOrderId,
                           @RequestParam(value = "price", required = false) Integer price,
                           @RequestParam(value = "payStatus", required = false) Integer payStatus,
                           @RequestParam(value = "payOrderStep", required = false) Integer payOrderStep,
                           @RequestParam(value = "sourceName", required = false) String sourceName) {
        int chargeOrderId = Integer.parseInt(userOrderId, 16);
        ThPayOrder thOrder = orderService.findThOrder(chargeOrderId);

        Order order = new Order();
        try {
            String userToken = thOrder.getUserToken();
            Date tokenRegTime = thOrder.getTokenRegTime();
            Date orderCreateTime = thOrder.getCreateTime();

            order.setChargeOrderId(chargeOrderId);
            order.setThirdPartyKey(account);
            order.setLinkId(orderno + payOrderStep);
            order.setPayType(YUANLANGPAY + "_" + "3");
            order.setUserToken(StringUtils.isEmpty(userToken) ? "" : userToken);
            order.setAmounts(price);
            order.setProdIdentification(sourceName);
            int productId = Integer.parseInt(CacheContainer.getProductIdByTpk("t_p_k_" + account));
            order.setUserId(Integer.parseInt(CacheContainer.getUserId("p_i_k_" + productId + "_" + sourceName)));
            order.setUserCompany(CacheContainer.getUserCompanyName("p_i_k_" + productId + "_" + sourceName));
            order.setProductId(Integer.parseInt(CacheContainer.getProductIdByTpk("t_p_k_" + account)));
            order.setProductName(CacheContainer.getProductNameByTpk("t_p_k_" + account));
            order.setStatus(payStatus == 1 ? 0 : 1);
            order.setRegisterTime(tokenRegTime);
            order.setCreateTime(orderCreateTime);

            this.orderService.addOrder(order);

            if (payStatus == 1) {
                HashMap<String, Object> updateParams = new HashMap<String, Object>();

                updateParams.put("id", chargeOrderId);
                updateParams.put("amounts", price);
                orderService.updateChargeOrder(updateParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.debug("addOrder:{} order exception:{}", order, ExceptionUtils.getMessage(e));
        }
        return "success";
    }


    @RequestMapping("queryThOrders.do")
    public ModelAndView queryThOrders(@RequestParam(value = "appId", required = false, defaultValue = "") String appId,
                                      @RequestParam(value = "channelId", required = false) String channelId,
                                      @RequestParam(value = "feepointId", required = false) String feepointId,
                                      @RequestParam(value = "payId", required = false) Integer payId,
                                      @RequestParam(value = "provinceId", required = false) Integer provinceId,
                                      @RequestParam(value = "statusCode", required = false) Integer statusCode,
                                      @RequestParam(value = "startDate", required = false) String startDate,
                                      @RequestParam(value = "endDate", required = false) String endDate,
                                      @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex) {


        PageInfo pageInfo = new PageInfo();
        pageInfo.setStartPageIndex(pageIndex);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("appId", appId);
        params.put("channelId", channelId);
        params.put("productId", feepointId);
        params.put("payId", payId);
        params.put("provinceId", provinceId);
        params.put("statusCode", statusCode);

        startDate = startDate == null ? DateUtils.specifyDate(0) : startDate;
        endDate = endDate == null ? DateUtils.specifyDate(0) : endDate;

        params.put("startDate", startDate);
        params.put("endDate", endDate);

        pageInfo = orderService.queryThOrders(pageInfo, params);

        PageUtils.set(pageInfo, "queryThOrders.do", params);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("pageInfo", pageInfo);
        model.put("selectProductId", StringUtils.isNotEmpty(appId) ? appId.split("\\|")[1] : appId);
        model.put("selectChannelId", channelId);
        model.put("selectFeepointId", feepointId);
        model.put("selectPayId", payId);
        model.put("selectProvinceId", provinceId);
        model.put("selectStatusCode", statusCode);
        model.put("startDate", startDate);
        model.put("endDate", endDate);
        model.put("products", productService.queryProduct(null));
        model.put("payIds", thPayService.queryThPay());

        if (StringUtils.isNotEmpty(appId)) {
            ProductIdentification identification = new ProductIdentification();
            identification.setProductId(Integer.parseInt(appId.split("\\|")[0]));

            List<ProductIdentification> productIdentifications = productIdentificationService.queryProductIdentification(identification);
            model.put("channelIds", productIdentifications);

            HashMap<String, Object> feePointsParams = new HashMap<String, Object>();
            feePointsParams.put("productId", Integer.valueOf(appId.split("\\|")[0]));

            List<FeePoint> feePoints = productDetialService.queryFeePointInfo(feePointsParams);
            model.put("feepointIds", feePoints);

        }
        return new ModelAndView("thOrder", model);
    }


    @RequestMapping("channelIdsload.do")
    @ResponseBody
    public Object queryChannelIds(@RequestParam("appId") String appId) {
        ProductIdentification identification = new ProductIdentification();

        identification.setProductId(Integer.parseInt(appId.split("\\|")[0]));

        List<ProductIdentification> productIdentifications = productIdentificationService.queryProductIdentification(identification);

        return productIdentifications;
    }

    @RequestMapping("feepointIdsload")
    @ResponseBody
    public Object queryFeepointIds(@RequestParam("appId") String appId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("productId", Integer.valueOf(appId.split("\\|")[0]));

        List<FeePoint> feePoints = productDetialService.queryFeePointInfo(params);
        return feePoints;
    }


}
