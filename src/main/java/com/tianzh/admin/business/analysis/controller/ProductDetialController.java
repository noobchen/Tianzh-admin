package com.tianzh.admin.business.analysis.controller;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.CacheContainer;
import com.tianzh.admin.business.analysis.model.FeePoint;
import com.tianzh.admin.business.analysis.model.Product;
import com.tianzh.admin.business.analysis.model.ProductDetial;
import com.tianzh.admin.business.analysis.service.ProductDetialService;
import com.tianzh.admin.business.analysis.service.ProductService;
import com.tianzh.admin.business.analysis.utils.KeyGenerateUtils;
import com.tianzh.admin.common.page.PageInfo;
import com.tianzh.admin.common.page.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pig on 2015-06-09.
 */
@Controller
public class ProductDetialController {

    @Autowired
    ProductDetialService productDetialService;


    public ModelAndView queryProductDetial(Integer productId, Integer pageIndex) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setStartPageIndex(pageIndex);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);

        String productName = CacheContainer.getProductName(Constant.ReferenceKey.PRODUCTKEY + productId);

        pageInfo = productDetialService.queryProductDetials(pageInfo, params);
        List<FeePoint> feePoints = productDetialService.queryFeePointInfo(params);

        PageUtils.set(pageInfo, "product.do", params);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("pageInfo", pageInfo);
        model.put("productId", productId);
        model.put("feePoints", feePoints);
        model.put("productName", productName);

        return new ModelAndView("productDetial", model);
    }


    @RequestMapping(value = "productDetialEdit.do", method = RequestMethod.POST)
    public ModelAndView editProductDetial(@RequestParam(value = "editProductId", required = false) Integer productId,
                                          @RequestParam(value = "editKeyType", required = false) Integer keyType,
                                          @RequestParam(value = "editThirdPartyKey", required = false) String thirdPartyKey,
                                          @RequestParam(value = "editThirdPartyAccount", required = false) String thirdPartyAccount,
                                          @RequestParam(value = "editThirdPartyPwd", required = false) String thirdPartyPwd,
                                          @RequestParam(value = "editStatus", required = false) Integer status,
                                          @RequestParam(value = "editId", required = false) Integer id) {


        ProductDetial productDetial = new ProductDetial();

        productDetial.setId(id);
        productDetial.setProductId(productId);

        String productName = CacheContainer.getProductName(Constant.ReferenceKey.PRODUCTKEY + productId);
        productDetial.setProductName(productName);

        productDetial.setKeyType(keyType);
        productDetial.setThirdPartyKey(thirdPartyKey);
        productDetial.setThirdPartyAccount(thirdPartyAccount);
        productDetial.setThirdPartyPwd(thirdPartyPwd);
        productDetial.setStatus(status);


        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productDetialService.editProductDetial(productDetial);

            //成功
            model.put("result", true);
            model.put("errorMsg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("editProductDetial:{} fail exception:{}", productDetial, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "修改失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=detial&productId=" + productId), model);
    }


    @RequestMapping(value = "feePointEdit.do", method = RequestMethod.POST)
    public ModelAndView editFeePoint(@RequestParam(value = "editFeePointId", required = false) Integer editFeePointId,
                                     @RequestParam(value = "editFeeProductId", required = false) Integer productId,
                                     @RequestParam(value = "editFeePointStr", required = false) String editFeePointStr,
                                     @RequestParam(value = "editFeePointName", required = false) String editFeePointName,
                                     @RequestParam(value = "editFeePointPrice", required = false) Integer editFeePointPrice,
                                     @RequestParam(value = "editFeePointDesc", required = false) String editFeePointDesc) {


        FeePoint feePoint = new FeePoint();

        feePoint.setId(editFeePointId);
        feePoint.setFeePointId(editFeePointStr);
        feePoint.setFeePointName(editFeePointName);
        feePoint.setPrice(editFeePointPrice);
        feePoint.setFeePointDesc(editFeePointDesc);

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productDetialService.editFeePointInfo(feePoint);

            //成功
            model.put("feePointresult", true);
            model.put("feePointerrorMsg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("editFeePoint:{} fail exception:{}", feePoint, ExceptionUtils.getStackTrace(e));
            model.put("feePointresult", false);
            model.put("feePointerrorMsg", "修改失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=detial&productId=" + productId), model);
    }


    @RequestMapping(value = "letuEdit.do", method = RequestMethod.POST)
    public ModelAndView editFeePointLetu(@RequestParam(value = "editLetuId", required = false) Integer editLetuId,
                                         @RequestParam(value = "editLetuPayPointNum", required = false) Integer editLetuPayPointNum,
                                         @RequestParam(value = "editLetuSdkChannelId", required = false) String editLetuSdkChannelId,
                                         @RequestParam(value = "feePointStr", required = false) String feePointStr,
                                         @RequestParam(value = "editLetuShowUIKey", required = false) String editLetuShowUIKey,
                                         @RequestParam(value = "editLetuMerchantKey", required = false) String editLetuMerchantKey,
                                         @RequestParam(value = "editLetuPayPrice", required = false) String editLetuPayPrice,
                                         @RequestParam(value = "editLetuPayFeeDesc", required = false) String editLetuPayFeeDesc,
                                         @RequestParam(value = "editLetuPayType", required = false) Integer editLetuPayType,
                                         @RequestParam(value = "editLetuGameType", required = false) Integer editLetuGameType
    ) {


        FeePoint feePoint = new FeePoint();

        feePoint.setId(editLetuId);
        feePoint.setFeePointId(feePointStr);
        feePoint.setLetuPayPointNum(editLetuPayPointNum);
        feePoint.setLetuSdkChannelId(editLetuSdkChannelId);
        feePoint.setLetuShowUIKey(editLetuShowUIKey);
        feePoint.setLetuMerchantKey(editLetuMerchantKey);
        feePoint.setLetuPayType(editLetuPayType);
        feePoint.setLetuGameType(editLetuGameType);
        feePoint.setLetuPrice(editLetuPayPrice);
        feePoint.setLetuFeeDesc(editLetuPayFeeDesc);

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productDetialService.editFeePointLetu(feePoint);

            //成功
            model.put("leturesult", true);
            model.put("letuerrorMsg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("editFeePointLetu:{} fail exception:{}", feePoint, ExceptionUtils.getStackTrace(e));
            model.put("leturesult", false);
            model.put("letuerrorMsg", "修改失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=feepoint&feepointId=" + editLetuId+"&feePointStr="+feePointStr), model);
    }


    @RequestMapping(value = "ylEdit.do", method = RequestMethod.POST)
    public ModelAndView editFeePointYl(@RequestParam(value = "editYlId", required = false) Integer editYlId,
                                       @RequestParam(value = "editYlIsOnline", required = false) Integer editYlIsOnline,
                                       @RequestParam(value = "editYlGoodsId", required = false) String editYlGoodsId,
                                       @RequestParam(value = "feePointStr", required = false) String feePointStr,
                                       @RequestParam(value = "editYlPrice", required = false) String editYlPrice,
                                       @RequestParam(value = "editYlFeeDesc", required = false) String editYlFeeDesc
    ) {


        FeePoint feePoint = new FeePoint();

        feePoint.setId(editYlId);
        feePoint.setFeePointId(feePointStr);
        feePoint.setyLIsOnline(editYlIsOnline);
        feePoint.setyLGoodsId(editYlGoodsId);
        feePoint.setyLPrice(editYlPrice);
        feePoint.setyLFeeDesc(editYlFeeDesc);

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productDetialService.editFeePointYL(feePoint);

            //成功
            model.put("ylresult", true);
            model.put("ylerrorMsg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("editFeePointYl:{} fail exception:{}", feePoint, ExceptionUtils.getStackTrace(e));
            model.put("ylresult", false);
            model.put("ylerrorMsg", "修改失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=feepoint&feepointId=" + editYlId+"&feePointStr="+feePointStr), model);
    }

    @RequestMapping(value = "zhangEdit.do", method = RequestMethod.POST)
    public ModelAndView editFeePointZhang(@RequestParam(value = "editzhangId", required = false) Integer editzhangId,
                                          @RequestParam(value = "feePointStr", required = false) String feePointStr,
                                          @RequestParam(value = "editzhangPriceId", required = false) String editzhangPriceId,
                                          @RequestParam(value = "editzhangKey", required = false) String editzhangKey,
                                          @RequestParam(value = "editzhangPrice", required = false) String editzhangPrice,
                                          @RequestParam(value = "editzhangFeeDesc", required = false) String editzhangFeeDesc,
                                          @RequestParam(value = "editzhangAppVersion", required = false) String editzhangAppVersion
    ) {

        FeePoint feePoint = new FeePoint();

        feePoint.setId(editzhangId);
        feePoint.setFeePointId(feePointStr);
        feePoint.setZhangKey(editzhangKey);
        feePoint.setZhPrice(editzhangPrice);
        feePoint.setZhFeeDesc(editzhangFeeDesc);
        feePoint.setZhangPricePointId(editzhangPriceId);
        feePoint.setZhangAppVersion(editzhangAppVersion);

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productDetialService.editFeePointZhang(feePoint);

            //成功
            model.put("zhangresult", true);
            model.put("zhangerrorMsg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("editFeePointZhang:{} fail exception:{}", feePoint, ExceptionUtils.getStackTrace(e));
            model.put("zhangresult", false);
            model.put("zhangerrorMsg", "修改失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=feepoint&feepointId=" + editzhangId+"&feePointStr="+feePointStr), model);
    }

    @RequestMapping(value = "productDetialAdd.do", method = RequestMethod.POST)
    public ModelAndView addProductDetial(
            @RequestParam(value = "addProductId", required = false) Integer productId,
            @RequestParam(value = "addKeyType", required = false) Integer keyType,
            @RequestParam(value = "addThirdPartyKey", required = false) String addThirdPartyKey,
            @RequestParam(value = "addThirdPartyAccount", required = false) String addThirdPartyAccount,
            @RequestParam(value = "addThirdPartyPwd", required = false) String addThirdPartyPwd
    ) {


        ProductDetial productDetial = new ProductDetial();

        productDetial.setProductId(productId);

        String productName = CacheContainer.getProductName(Constant.ReferenceKey.PRODUCTKEY + productId);
        productDetial.setProductName(productName);

        productDetial.setKeyType(keyType);
        productDetial.setThirdPartyKey(addThirdPartyKey);
        productDetial.setThirdPartyAccount(addThirdPartyAccount);
        productDetial.setThirdPartyPwd(addThirdPartyPwd);
        productDetial.setStatus(Constant.ProductStatus.NORMAL);
        productDetial.setCreateTime(Calendar.getInstance().getTime());

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productDetialService.addProductDetial(productDetial);

            //成功
            model.put("result", true);
            model.put("errorMsg", "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("addProductDetial:{} fail exception:{}", productDetial, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "增加失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=detial&productId=" + productId), model);
    }

    @RequestMapping(value = "feePointAdd.do", method = RequestMethod.POST)
    public ModelAndView addFeePoint(
            @RequestParam(value = "addFeeProductId", required = false) Integer productId,
            @RequestParam(value = "addFeeProductName", required = false) String productName,
            @RequestParam(value = "addFeeName", required = false) String feeName,
            @RequestParam(value = "addFeePrice", required = false) Integer feePrice,
            @RequestParam(value = "addLetuPayPointNum", required = false) Integer letuPayPointNum,
            @RequestParam(value = "addLetuSdkChannelId", required = false) String letuSdkChannelId,
            @RequestParam(value = "addLetuPayType", required = false) Integer letuPayType,
            @RequestParam(value = "addLetuGameType", required = false) Integer letuGameType,
            @RequestParam(value = "addLetuShowUIKey", required = false) String letuShowUIKey,
            @RequestParam(value = "addLetuMerchantKey", required = false) String addLetuMerchantKey,
            @RequestParam(value = "addLetuPrice", required = false) String addLetuPrice,
            @RequestParam(value = "addLetuFeeDesc", required = false) String addLetuFeeDesc,
            @RequestParam(value = "addYLIsOnline", required = false) Integer yLIsOnline,
            @RequestParam(value = "addYLGoodsId", required = false) String yLGoodsId,
            @RequestParam(value = "addYlPrice", required = false) String addYlPrice,
            @RequestParam(value = "addYlFeeDesc", required = false) String addYlFeeDesc,
            @RequestParam(value = "addZhangKey", required = false) String zhangKey,
            @RequestParam(value = "addZhPrice", required = false) String addZhPrice,
            @RequestParam(value = "addZhFeeDesc", required = false) String addZhFeeDesc,
            @RequestParam(value = "addZhangPricePointId", required = false) String addZhangPricePointId,
            @RequestParam(value = "addZhangAppVersion", required = false) String zhangAppVersion
    ) {

        FeePoint feePoint = new FeePoint();

        feePoint.setProductId(productId);
        feePoint.setProductName(productName);
        feePoint.setFeePointId(KeyGenerateUtils.generateProductId());
        feePoint.setFeePointName(feeName);
        feePoint.setPrice(feePrice);
        feePoint.setLetuPayPointNum(letuPayPointNum);
        feePoint.setLetuSdkChannelId(letuSdkChannelId);
        feePoint.setLetuPayType(letuPayType);
        feePoint.setLetuGameType(letuGameType);
        feePoint.setLetuShowUIKey(letuShowUIKey);
        feePoint.setLetuMerchantKey(addLetuMerchantKey);
        feePoint.setLetuFeeDesc(addLetuFeeDesc);
        feePoint.setLetuPrice(StringUtils.isEmpty(addLetuPrice) ? feePrice.toString() : addLetuPrice);
        feePoint.setyLIsOnline(yLIsOnline);
        feePoint.setyLGoodsId(yLGoodsId);
        feePoint.setyLPrice(StringUtils.isEmpty(addYlPrice) ? feePrice.toString() : addYlPrice);
        feePoint.setyLFeeDesc(addYlFeeDesc);
        feePoint.setZhangKey(zhangKey);
        feePoint.setZhangPricePointId(addZhangPricePointId);
        feePoint.setZhangAppVersion(zhangAppVersion);
        feePoint.setZhPrice(StringUtils.isEmpty(addZhPrice) ? feePrice.toString() : addZhPrice);
        feePoint.setZhFeeDesc(addZhFeeDesc);
        feePoint.setCreateTime(Calendar.getInstance().getTime());

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productDetialService.addFeePointInfo(feePoint);
            productDetialService.addFeePointYL(feePoint);
            productDetialService.addFeePointZhang(feePoint);
            productDetialService.addFeePointLetu(feePoint);


            //成功
            model.put("feePointresult", true);
            model.put("feePointerrorMsg", "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("addFeePoint:{} fail exception:{}", feePoint, ExceptionUtils.getStackTrace(e));
            model.put("feePointresult", false);
            model.put("feePointerrorMsg", "增加失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=detial&productId=" + productId), model);
    }


    @RequestMapping(value = "productDetialDel.do", method = RequestMethod.POST)
    public ModelAndView delProductDetial(@RequestParam(value = "deleteId", required = false) Integer id,
                                         @RequestParam(value = "delProductId", required = false) Integer productId
    ) {
        Map<String, Object> model = new HashMap<String, Object>();
        ProductDetial productDetial = null;
        try {
            productDetial = new ProductDetial();

            productDetial.setId(id);
            productDetialService.delProductDetial(productDetial);

            //成功
            model.put("result", true);
            model.put("errorMsg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("delProductDetial:{} fail exception:{}", productDetial, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "删除失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=detial&productId=" + productId), model);
    }

    @RequestMapping(value = "feePointDel.do", method = RequestMethod.POST)
    public ModelAndView delFeePoint(@RequestParam(value = "delFeePointId", required = false) Integer delFeePointId,
                                    @RequestParam(value = "delFeeProductId", required = false) Integer productId,
                                    @RequestParam(value = "delFeePointStr", required = false) String delFeePointStr
    ) {
        Map<String, Object> model = new HashMap<String, Object>();
        FeePoint feePoint = null;
        try {
            feePoint = new FeePoint();

            feePoint.setId(delFeePointId);
            feePoint.setFeePointId(delFeePointStr);
            productDetialService.delFeePointInfo(feePoint);

            //成功
            model.put("feePointresult", true);
            model.put("feePointerrorMsg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("delFeePoint:{} fail exception:{}", feePoint, ExceptionUtils.getStackTrace(e));
            model.put("feePointresult", false);
            model.put("feePointerrorMsg", "删除失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=detial&productId=" + productId), model);
    }

    @RequestMapping(value = "letuDel.do", method = RequestMethod.POST)
    public ModelAndView delLetuFeePoint(@RequestParam(value = "deleteLetuId", required = false) Integer deleteLetuId,
                                        @RequestParam(value = "feePointStr", required = false) String feePointStr,
                                        @RequestParam(value = "delLetuPayPointNum", required = false) String delLetuPayPointNum
    ) {
        Map<String, Object> model = new HashMap<String, Object>();
        FeePoint feePoint = null;
        try {
            feePoint = new FeePoint();

            feePoint.setId(deleteLetuId);
            feePoint.setFeePointId(feePointStr);
            productDetialService.delFeePointLetu(feePoint);

            //成功
            model.put("leturesult", true);
            model.put("letuerrorMsg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("delLetuFeePoint:{} fail exception:{}", feePoint, ExceptionUtils.getStackTrace(e));
            model.put("leturesult", false);
            model.put("letuerrorMsg", "删除失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=feepoint&feepointId=" + deleteLetuId+"&feePointStr="+feePointStr), model);
    }


    @RequestMapping(value = "ylDel.do", method = RequestMethod.POST)
    public ModelAndView delYlFeePoint(@RequestParam(value = "deleteYlId", required = false) Integer deleteYlId,
                                      @RequestParam(value = "feePointStr", required = false) String feePointStr,
                                      @RequestParam(value = "delYlGoodsId", required = false) String delYlGoodsId
    ) {
        Map<String, Object> model = new HashMap<String, Object>();
        FeePoint feePoint = null;
        try {
            feePoint = new FeePoint();

            feePoint.setId(deleteYlId);
            feePoint.setFeePointId(feePointStr);
            productDetialService.delFeePointYL(feePoint);

            //成功
            model.put("ylresult", true);
            model.put("ylerrorMsg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("delYlFeePoint:{} fail exception:{}", feePoint, ExceptionUtils.getStackTrace(e));
            model.put("ylresult", false);
            model.put("ylerrorMsg", "删除失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=feepoint&feepointId=" + deleteYlId+"&feePointStr="+feePointStr), model);
    }


    @RequestMapping(value = "zhangDel.do", method = RequestMethod.POST)
    public ModelAndView delZhangFeePoint(@RequestParam(value = "deleteZhangId", required = false) Integer deleteZhangId,
                                         @RequestParam(value = "feePointStr", required = false) String feePointStr,
                                         @RequestParam(value = "delZhangPricePointId", required = false) String delZhangPricePointId
    ) {
        Map<String, Object> model = new HashMap<String, Object>();
        FeePoint feePoint = null;
        try {
            feePoint = new FeePoint();

            feePoint.setId(deleteZhangId);
            feePoint.setFeePointId(feePointStr);
            productDetialService.delFeePointZhang(feePoint);

            //成功
            model.put("zhangresult", true);
            model.put("zhangerrorMsg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("delZhangFeePoint:{} fail exception:{}", feePoint, ExceptionUtils.getStackTrace(e));
            model.put("zhangresult", false);
            model.put("zhangerrorMsg", "删除失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do?method=feepoint&feepointId=" + deleteZhangId+"&feePointStr="+feePointStr), model);
    }

    public ModelAndView queryThFeepoint(Integer feepointId,String feepointStr) {
        Map<String, Object> model = new HashMap<String, Object>();

        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("feepointId", feepointId);
        FeePoint feePointLetu = productDetialService.queryFeePointLetu(params);
        FeePoint feePointYL = productDetialService.queryFeePointYL(params);
        FeePoint feePointZhang = productDetialService.queryFeePointZhang(params);

        model.put("feePointLetu", feePointLetu);
        model.put("feePointYL", feePointYL);
        model.put("feePointZhang", feePointZhang);
        model.put("feePointStr", feepointStr);

        return new ModelAndView("productFeePoint", model);

    }
}
