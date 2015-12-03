package com.tianzh.admin.business.analysis.controller;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.CacheContainer;
import com.tianzh.admin.business.analysis.model.Product;
import com.tianzh.admin.business.analysis.model.ProductDetial;
import com.tianzh.admin.business.analysis.model.ProductIdentification;
import com.tianzh.admin.business.analysis.service.ProductIdentificationService;
import com.tianzh.admin.business.analysis.service.ProductService;
import com.tianzh.admin.common.page.PageInfo;
import com.tianzh.admin.common.page.PageUtils;
import com.tianzh.admin.common.permission.service.OperatorService;
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
import java.util.Map;

/**
 * Created by pig on 2015-06-09.
 */
@Controller
public class ProductIdentificationController {

    @Autowired
    ProductIdentificationService productIdentificationService;

    @Autowired
    ProductService productService;

    @Autowired
    OperatorService operatorService;

    @RequestMapping("productIdentification.do")
    public ModelAndView queryProductIdentification(@RequestParam(value = "productId", required = false, defaultValue = "") Integer productId,
                                                   @RequestParam(value = "prodIdentification", required = false) String prodIdentification,
                                                   @RequestParam(value = "userId", required = false) Integer userId,
                                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setStartPageIndex(pageIndex);

        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("productId", productId);
        params.put("prodIdentification", prodIdentification);
        params.put("userId", userId);

        pageInfo = productIdentificationService.queryProductIdentification(pageInfo, params);

        PageUtils.set(pageInfo, "productIdentification.do", params);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("pageInfo", pageInfo);
        model.put("productId", productId);
        model.put("prodIdentification", prodIdentification);
        model.put("userId", userId);
        model.put("products", productService.queryProduct(null));
        model.put("userIds", operatorService.query(null));

        return new ModelAndView("productIdentification", model);
    }


    @RequestMapping(value = "productIdentificationEdit.do", method = RequestMethod.POST)
    public ModelAndView editProductIdentification(@RequestParam(value = "editId", required = false) Integer id,
                                                  @RequestParam(value = "editProductId", required = false) Integer editProductId,
                                                  @RequestParam(value = "editUserId", required = false) Integer editUserId,
                                                  @RequestParam(value = "editProductIdentification", required = false) String identi,
                                                  @RequestParam(value = "editUserCompanyName", required = false) String companyName,
                                                  @RequestParam(value = "editCooperationType", required = false) Integer cooperationType,
                                                  @RequestParam(value = "editDiscount", required = false, defaultValue = "0.0") String discount,
                                                  @RequestParam(value = "editSharing", required = false, defaultValue = "0.0") String sharing,
                                                  @RequestParam(value = "editChargeOffType", required = false) Integer chargeOffType) {
        ProductIdentification identification = new ProductIdentification();

        identification.setId(id);
        identification.setProductId(editProductId);
        identification.setUserId(editUserId);
        identification.setProdIdentification(identi);
        identification.setUserCompanyName(companyName);
        identification.setCooperationType(cooperationType);
        identification.setDiscount(Float.parseFloat(discount.equals("-/-") ? "0.0" : discount));
        identification.setAccountType(identi.equals("-/-") ? "1" : "3");
        identification.setChargeOffType(chargeOffType);
        identification.setSharing(Float.parseFloat(sharing.equals("-/-") ? "0.0" : sharing));

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productIdentificationService.editProductIdentification(identification);

            //成功
            model.put("result", true);
            model.put("errorMsg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("editProductIdentification:{} fail exception:{}", identification, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "修改失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("productIdentification.do"), model);
    }


    @RequestMapping(value = "productIdentificationAdd.do", method = RequestMethod.POST)
    public ModelAndView addProductIdentification(@RequestParam(value = "addUserId", required = false) String userIdStr,
                                                 @RequestParam(value = "addProductId", required = false) String productIdStr,
                                                 @RequestParam(value = "addProdIdentification", required = false) String prodIdentification,
                                                 @RequestParam(value = "addCooperationType", required = false) Integer cooperationType,
                                                 @RequestParam(value = "addDiscount", required = false, defaultValue = "0.0") String discount,
                                                 @RequestParam(value = "addSharing", required = false, defaultValue = "0.0") String sharing,
                                                 @RequestParam(value = "addChargeOffType", required = false) Integer chargeOffType
    ) {


        ProductIdentification identification = new ProductIdentification();
        identification.setUserId(Integer.parseInt(userIdStr.split("\\|")[0]));
        identification.setUserCompanyName((userIdStr.split("\\|")[1]));
        identification.setAccountType((userIdStr.split("\\|")[2]));
        identification.setProductId(Integer.parseInt(productIdStr.split("\\|")[0]));
        identification.setProductName(productIdStr.split("\\|")[1]);
        identification.setProdIdentification(prodIdentification);
        identification.setCooperationType(cooperationType);
        identification.setChargeOffType(chargeOffType);
        identification.setDiscount(Float.parseFloat(StringUtils.isEmpty(discount) ? "0.0" : discount));
        identification.setSharing(Float.parseFloat(StringUtils.isEmpty(sharing) ? "0.0" : sharing));
        identification.setStatus(Constant.ProductStatus.NORMAL);
        identification.setCreateTime(Calendar.getInstance().getTime());

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productIdentificationService.addProductIdentification(identification);

            //成功
            model.put("result", true);
            model.put("errorMsg", "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("addProductIdentification:{} fail exception:{}", identification, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "增加失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("productIdentification.do"), model);
    }


    @RequestMapping(value = "productIdentificationDel.do", method = RequestMethod.POST)
    public ModelAndView delProductIdentification(@RequestParam(value = "deleteId", required = false) Integer id
    ) {

        Map<String, Object> model = new HashMap<String, Object>();
        ProductIdentification identification = null;
        try {
            identification = new ProductIdentification();

            identification.setId(id);
            productIdentificationService.delProductIdentification(identification);

            //成功
            model.put("result", true);
            model.put("errorMsg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("delProductIdentification:{} fail exception:{}", identification, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "删除失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("productIdentification.do"), model);
    }
}
