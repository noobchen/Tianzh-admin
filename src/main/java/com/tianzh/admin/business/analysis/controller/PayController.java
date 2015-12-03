package com.tianzh.admin.business.analysis.controller;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.model.ThPayProvince;
import com.tianzh.admin.business.analysis.service.ThPayProvinceService;
import com.tianzh.admin.business.analysis.service.ThPayService;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.ThPay;
import com.tianzh.admin.business.analysis.utils.ProvinceUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

/**
 * Created by pig on 2015-09-15.
 */
@Controller
public class PayController {
    @Autowired
    ThPayService thPayService;

    @Autowired
    ThPayProvinceService thPayProvinceService;

    @RequestMapping("thpayManager.do")
    public ModelAndView queryThpay() {
        List<ThPay> thPays = thPayService.queryThPay();

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("thPays", thPays);
        return new ModelAndView("thPay", model);
    }


    @RequestMapping("thpayProManager.do")
    public ModelAndView queryThpayProManager(@RequestParam(value = "thPayId", required = false, defaultValue = "") Integer thPayId,
                                             @RequestParam(value = "method", required = false, defaultValue = "") String method
    ) {
        if (method.equals("detial")) {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("thPayId", thPayId);
            params.put("provinceType", 0);

            params.put("providerId", Constant.Provider.CHINA_MOBILE);
            List<ThPayProvince> mobileProvinces = thPayProvinceService.queryProvinces(params);
            params.put("providerId", Constant.Provider.CHINA_UNICOM);
            List<ThPayProvince> unicomProvinces = thPayProvinceService.queryProvinces(params);
            params.put("providerId", Constant.Provider.CHINA_TELECOM);
            List<ThPayProvince> telecomProvinces = thPayProvinceService.queryProvinces(params);

            Map<String, Object> model = new HashMap<String, Object>();

            model.put("mobileProvinces", ProvinceUtils.getProvinceStr(mobileProvinces));
            model.put("unicomProvinces", ProvinceUtils.getProvinceStr(unicomProvinces));
            model.put("telecomProvinces", ProvinceUtils.getProvinceStr(telecomProvinces));
            model.put("thPayId", thPayId);

            return new ModelAndView("thPayProvince", model);
        } else {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("thPayId", thPayId);
            params.put("provinceType", 0);

            params.put("providerId", Constant.Provider.CHINA_MOBILE);
            List<ThPayProvince> mobileProvinces = thPayProvinceService.queryProvinces(params);
            params.put("providerId", Constant.Provider.CHINA_UNICOM);
            List<ThPayProvince> unicomProvinces = thPayProvinceService.queryProvinces(params);
            params.put("providerId", Constant.Provider.CHINA_TELECOM);
            List<ThPayProvince> telecomProvinces = thPayProvinceService.queryProvinces(params);

            params.put("provinceType", 1);

            params.put("providerId", Constant.Provider.CHINA_MOBILE);
            List<ThPayProvince> advantageMobileProvinces = thPayProvinceService.queryProvinces(params);
            params.put("providerId", Constant.Provider.CHINA_UNICOM);
            List<ThPayProvince> advantageUnicomProvinces = thPayProvinceService.queryProvinces(params);
            params.put("providerId", Constant.Provider.CHINA_TELECOM);
            List<ThPayProvince> advantageTelecomProvinces = thPayProvinceService.queryProvinces(params);

            Map<String, Object> model = new HashMap<String, Object>();

            model.put("mobileProvinces", mobileProvinces);
            model.put("unicomProvinces", unicomProvinces);
            model.put("telecomProvinces", telecomProvinces);
            model.put("advantageMobileProvinces", advantageMobileProvinces);
            model.put("advantageUnicomProvinces", advantageUnicomProvinces);
            model.put("advantageTelecomProvinces", advantageTelecomProvinces);
            model.put("thPayId", thPayId);

            return new ModelAndView("thPayAdvantageProvince", model);
        }
    }


    @RequestMapping(value = "thPayAdd.do", method = RequestMethod.POST)
    public ModelAndView addThPay(@RequestParam(value = "addName", required = true) String name,
                                 @RequestParam(value = "addWeight", required = true) Integer weight,
                                 @RequestParam(value = "addStatus", required = true) Integer status
    ) {
        ThPay thPay = new ThPay();

        thPay.setName(name);
        thPay.setWeight(weight);
        thPay.setStatus(status);
        thPay.setCreateTime(Calendar.getInstance().getTime());

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            thPayService.addThPay(thPay);

            //成功
            model.put("result", true);
            model.put("errorMsg", "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("addThPay:{} fail exception:{}", thPay, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "增加失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("thpayManager.do"), model);
    }


    @RequestMapping(value = "thPayProvinceAdd.do", method = RequestMethod.POST)
    public ModelAndView addThPayProvince(@RequestParam(value = "provinces", required = true) String[] provinces,
                                         @RequestParam(value = "thPayId", required = true) Integer thPayId,
                                         @RequestParam(value = "provinceType", required = true) Integer provinceType,
                                         @RequestParam(value = "providerId", required = true) Integer providerId
    ) {

        List<ThPayProvince> thPayProvinces = new ArrayList<ThPayProvince>();

        for (String province : provinces) {
            ThPayProvince thPayProvince = new ThPayProvince();

            thPayProvince.setProvinceType(provinceType);
            thPayProvince.setThPayId(thPayId);
            thPayProvince.setProvinderId(providerId);
            thPayProvince.setProvinceId(Integer.parseInt(province.split("\\|")[0]));
            thPayProvince.setProvince(province.split("\\|")[1]);
            thPayProvince.setCreateTime(Calendar.getInstance().getTime());

            thPayProvinces.add(thPayProvince);
        }

        Map<String, Object> model = new HashMap<String, Object>();
        String result = providerId == 0 ? "mobileresult" : providerId == 1 ? "unicomresult" : "telecomresult";
        String errorMsg = providerId == 0 ? "mobileerrorMsg" : providerId == 1 ? "unicomerrorMsg" : "telecomerrorMsg";
        try {
            thPayProvinceService.addProvinces(thPayProvinces);

            //成功
            model.put(result, true);
            model.put(errorMsg, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("addThPayProvince:{} fail exception:{}", thPayProvinces, ExceptionUtils.getStackTrace(e));
            model.put(result, false);
            model.put(errorMsg, "增加失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("thpayProManager.do?thPayId=" + thPayId + (provinceType == 0 ? "&method=detial" : "&method=advantage")), model);
    }


    @RequestMapping(value = "thPayEdit.do", method = RequestMethod.POST)
    public ModelAndView editThPay(@RequestParam(value = "editName", required = true) String name,
                                  @RequestParam(value = "editWeight", required = true) Integer weight,
                                  @RequestParam(value = "editStatus", required = true) Integer status,
                                  @RequestParam(value = "editId", required = true) Integer id) {


        ThPay thPay = new ThPay();

        thPay.setId(id);
        thPay.setName(name);
        thPay.setWeight(weight);
        thPay.setStatus(status);

        Map<String, Object> model = new HashMap<String, Object>();

        try {
            thPayService.editThPay(thPay);

            //成功
            model.put("result", true);
            model.put("errorMsg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("editThPay:{} fail exception:{}", thPay, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "修改失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("thpayManager.do"), model);
    }

    @RequestMapping(value = "thPayDel.do", method = RequestMethod.POST)
    public ModelAndView delThPay(@RequestParam(value = "deleteId", required = true) Integer id
    ) {
        Map<String, Object> model = new HashMap<String, Object>();
        ThPay thPay = null;
        try {
            thPay = new ThPay();

            thPay.setId(id);
            thPayService.delThPay(thPay);

            //成功
            model.put("result", true);
            model.put("errorMsg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("delThPay:{} fail exception:{}", thPay, ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "删除失败，请联系管理员！");
        }

        return new ModelAndView(new RedirectView("thpayManager.do"), model);
    }
}
