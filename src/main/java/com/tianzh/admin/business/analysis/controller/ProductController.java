package com.tianzh.admin.business.analysis.controller;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.Product;
import com.tianzh.admin.business.analysis.service.ProductService;
import com.tianzh.admin.business.analysis.utils.KeyGenerateUtils;
import com.tianzh.admin.common.page.PageInfo;
import com.tianzh.admin.common.page.PageUtils;
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
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductDetialController detialController;

    @RequestMapping("product.do")
    public ModelAndView queryProduct(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                     @RequestParam(value = "productId", required = false) Integer productId,
                                     @RequestParam(value = "feepointId", required = false) Integer feepointId,
                                     @RequestParam(value = "feePointStr", required = false) String feepointStr,
                                     @RequestParam(value = "method", required = false) String method,
                                     @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex) {
        if (method != null && method.equals("detial")) {
            return detialController.queryProductDetial(productId, pageIndex);
        }

        if (method != null && method.equals("feepoint")) {
            return detialController.queryThFeepoint(feepointId,feepointStr);
        }

        PageInfo pageInfo = new PageInfo();
        pageInfo.setStartPageIndex(pageIndex);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);

        pageInfo = productService.queryProduct(pageInfo, params);

        PageUtils.set(pageInfo, "product.do", params);

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("pageInfo", pageInfo);
        model.put("name", name);

        return new ModelAndView("product", model);
    }


    @RequestMapping(value = "productEdit.do", method = RequestMethod.POST)
    public ModelAndView editProduct(@RequestParam(value = "editProductName", required = true) String name,
                                    @RequestParam(value = "editStatus", required = true) Integer status,
                                    @RequestParam(value = "editId", required = true) Integer id) {


        Product product = new Product();

        product.setId(id);
        product.setName(name);
        product.setStatus(status);


        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productService.editProduct(product);

            //成功
            model.put("result", true);
            model.put("errorMsg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("editProduct:{} fail exception:{}", product, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "修改失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do"), model);
    }


    @RequestMapping(value = "productAdd.do", method = RequestMethod.POST)
    public ModelAndView addProduct(@RequestParam(value = "addName", required = true) String name
    ) {


        Product product = new Product();

        product.setName(name);
        product.setAppId(KeyGenerateUtils.generateAppId());
        product.setStatus(Constant.ProductStatus.NORMAL);
        product.setCreateTime(Calendar.getInstance().getTime());

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            productService.addProduct(product);

            //成功
            model.put("result", true);
            model.put("errorMsg", "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("addProduct:{} fail exception:{}", product, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "增加失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do"), model);
    }


    @RequestMapping(value = "productDel.do", method = RequestMethod.POST)
    public ModelAndView delProduct(@RequestParam(value = "deleteId", required = true) Integer id
    ) {
        Map<String, Object> model = new HashMap<String, Object>();
        Product product = null;
        try {
            product = new Product();

            product.setId(id);
            productService.deleteProduct(product);

            //成功
            model.put("result", true);
            model.put("errorMsg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("delProduct:{} fail exception:{}", product, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "删除失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("product.do"), model);
    }
}
