package com.tianzh.admin.business.analysis.controller;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.CacheContainer;
import com.tianzh.admin.business.analysis.model.ProductIdentification;
import com.tianzh.admin.business.analysis.model.SdkBasicAnalysis;
import com.tianzh.admin.business.analysis.model.SdkProvinceAnalysis;
import com.tianzh.admin.business.analysis.service.OrderService;
import com.tianzh.admin.business.analysis.service.ProductIdentificationService;
import com.tianzh.admin.business.analysis.service.ProductService;
import com.tianzh.admin.business.analysis.service.ThPayService;
import com.tianzh.admin.business.analysis.utils.DateUtils;
import com.tianzh.admin.common.page.PageUtils;
import com.tianzh.admin.common.spring.mvc.session.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pig on 2015-10-17.
 */
@Controller
public class ChargeAnalysisController {

    @Autowired
    OrderService orderService;
    @Autowired
    private ProductIdentificationService productIdentificationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ThPayService thPayService;

    @RequestMapping("queryChargeBasicAnalysis.do")
    public ModelAndView queryChargeBasicAnalysis(@RequestParam(value = "productId", required = false) Integer productId,
                                                 @RequestParam(value = "prodIdentification", required = false) String prodIdentification,
                                                 @RequestParam(value = "userId", required = false) Integer userId,
                                                 @RequestParam(value = "payId", required = false) Integer payId,
                                                 @RequestParam(value = "startDate", required = false) String startDate,
                                                 @RequestParam(value = "endDate", required = false) String endDate,
                                                 @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex) {
        Map<String, Object> model = new HashMap<String, Object>();
        ArrayList<SdkBasicAnalysis> sdkBasicAnalysises = null;

        if (productId != null && prodIdentification != null && userId != null) {
            SessionUtils.saveSessionAttribute("chargeAnalysisProductId", productId);
            SessionUtils.saveSessionAttribute("chargeAnalysisProdId", prodIdentification);
            SessionUtils.saveSessionAttribute("chargeAnalysisUserId", userId);

            HashMap<String, String> params = new HashMap<String, String>();

            params.put("productId", CacheContainer.getProductKey(Constant.ReferenceKey.PRODUCTKEY + productId));
            params.put("prodIdentification", prodIdentification);
            if (payId != null) params.put("payId", String.valueOf(payId));
            params.put("startDate", startDate == null ? DateUtils.specifyDate(0) : startDate);
            params.put("endDate", endDate == null ? DateUtils.specifyDate(0) : endDate);

            try {
                sdkBasicAnalysises = (ArrayList<SdkBasicAnalysis>) orderService.queryBasicChargeAnalysises(params);
            } catch (Exception e) {
                e.printStackTrace();
                Log.exception.warn("queryChargeBasicAnalysis exception:{}", ExceptionUtils.getStackTrace(e));
                model.put("result", false);
                model.put("errorMsg", "查询失败，请联系管理员！");
            }


            ProductIdentification identi = new ProductIdentification();

            identi.setUserId(userId);
            identi.setProductId(productId);

            model.put("prodIdentifications", productIdentificationService.queryProIdentiGroup(identi));

            ProductIdentification userIdsIdenti = new ProductIdentification();
            userIdsIdenti.setProductId(productId);

            model.put("userIds", productIdentificationService.queryProIdentiGroup(userIdsIdenti));
        }

        model.put("productId", productId);
        model.put("prodIdentification", prodIdentification);
        model.put("userId", userId);
        model.put("startDate", startDate == null ? DateUtils.specifyDate(0) : startDate);
        model.put("endDate", endDate == null ? DateUtils.specifyDate(0) : endDate);
        model.put("products", productService.queryProduct(null));
        model.put("payIds", thPayService.queryThPay());
        model.put("sdkBasicAnalysises", sdkBasicAnalysises);

        return new ModelAndView("chargeBasicAnalysis", model);
    }

    @RequestMapping("queryBasicProvinceAnalysis.do")
    public ModelAndView queryBasicProvinceAnalysis(@RequestParam(value = "specifiedDate", required = false) String specifiedDate,
                                                   @RequestParam(value = "payType", required = false) String payType) {
        int productId = (Integer) SessionUtils.getSessionAttribute("chargeAnalysisProductId");
        String prodIdentification = (String) SessionUtils.getSessionAttribute("chargeAnalysisProdId");
        Integer userId = (Integer) SessionUtils.getSessionAttribute("chargeAnalysisUserId");

        String payId = payType.split("\\|")[0];
        String payName = payType.split("\\|")[1];

        Map<String, Object> model = new HashMap<String, Object>();

        HashMap<String, Object> params = new HashMap<String, Object>();

        String productRefKey = Constant.ReferenceKey.PRODUCTKEY + productId;

        params.put("productId", CacheContainer.getProductKey(productRefKey));
        params.put("prodIdentification", prodIdentification);
        params.put("payId", payId);
        params.put("specifiedDate", specifiedDate);

        //数据
        params.put("providerId", Constant.Provider.CHINA_MOBILE);
        ArrayList<SdkProvinceAnalysis> mobileAnalysises = orderService.queryBasicProvinceAnalysis(params);
        model.put("mobileAnalysises", mobileAnalysises);

        params.put("providerId", Constant.Provider.CHINA_UNICOM);
        ArrayList<SdkProvinceAnalysis> unicomAnalysises = orderService.queryBasicProvinceAnalysis(params);
        model.put("unicomAnalysises", unicomAnalysises);

        params.put("providerId", Constant.Provider.CHINA_TELECOM);
        ArrayList<SdkProvinceAnalysis> telecomAnalysises = orderService.queryBasicProvinceAnalysis(params);
        model.put("telecomAnalysises", telecomAnalysises);

        //产品名
        model.put("productName", CacheContainer.getProductName(productRefKey));

        //渠道
        String prodIdentiKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + prodIdentification;
        model.put("companyName", CacheContainer.getUserCompanyName(prodIdentiKey));
        //渠道号
        model.put("prodIdentification", prodIdentification);
        //SDK
        model.put("payName", payName);
        model.put("payType", payType);
        model.put("specifiedDate", specifiedDate);

        return new ModelAndView("basicProvinceAnalysis", model);
    }

    @RequestMapping("queryAllProvinceAnalysis.do")
    public ModelAndView queryBasicProvinceAnalysis(@RequestParam(value = "specifiedDate", required = false) String specifiedDate,
                                                   @RequestParam(value = "payType", required = false) String payType,
                                                   @RequestParam(value = "providerId", required = false) Integer providerId) {
        int productId = (Integer) SessionUtils.getSessionAttribute("chargeAnalysisProductId");
        String prodIdentification = (String) SessionUtils.getSessionAttribute("chargeAnalysisProdId");
        Integer userId = (Integer) SessionUtils.getSessionAttribute("chargeAnalysisUserId");

        String payId = payType.split("\\|")[0];
        String payName = payType.split("\\|")[1];

        Map<String, Object> model = new HashMap<String, Object>();

        HashMap<String, Object> params = new HashMap<String, Object>();

        String productRefKey = Constant.ReferenceKey.PRODUCTKEY + productId;

        params.put("productId", CacheContainer.getProductKey(productRefKey));
        params.put("prodIdentification", prodIdentification);
        params.put("payId", payId);
        params.put("specifiedDate", specifiedDate);

        //数据
        params.put("providerId", providerId);
        ArrayList<SdkProvinceAnalysis> provinceAnalysis = orderService.queryBasicProvinceAnalysis(params);
        model.put("provinceAnalysis", provinceAnalysis);

        //产品名
        model.put("productName", CacheContainer.getProductName(productRefKey));

        //渠道
        String prodIdentiKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + prodIdentification;
        model.put("companyName", CacheContainer.getUserCompanyName(prodIdentiKey));
        //渠道号
        model.put("prodIdentification", prodIdentification);
        //SDK
        model.put("payName", payName);
        model.put("payType", payType);
        model.put("providerName", providerId == 0 ? "中国移动" : providerId == 1 ? "中国联通" : "中国电信");

        return new ModelAndView("allProvinceAnalysis", model);
    }

}
