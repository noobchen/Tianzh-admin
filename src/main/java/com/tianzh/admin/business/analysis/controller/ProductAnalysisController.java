package com.tianzh.admin.business.analysis.controller;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.controller.ProductDetialController;
import com.tianzh.admin.business.analysis.dao.ProductDao;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.*;
import com.tianzh.admin.business.analysis.service.ProductAnalysisService;
import com.tianzh.admin.business.analysis.service.ProductIdentificationService;
import com.tianzh.admin.business.analysis.service.ProductService;
import com.tianzh.admin.business.analysis.utils.DateUtils;
import com.tianzh.admin.common.page.PageInfo;
import com.tianzh.admin.common.page.PageUtils;
import com.tianzh.admin.common.permission.model.Operator;
import com.tianzh.admin.common.permission.service.OperatorService;
import com.tianzh.admin.common.spring.mvc.session.SessionUtils;
import com.tianzh.admin.common.util.json.JsonUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pig on 2015-06-09.
 */
@Controller
public class ProductAnalysisController {

    @Autowired
    ProductAnalysisService productAnalysisService;

    @Autowired
    ProductService productService;

    @Autowired
    OperatorService operatorService;

    @Autowired
    ProductIdentificationService productIdentificationService;

    @RequestMapping("productAnalysis.do")
    public ModelAndView queryProductAnalysis(@RequestParam(value = "productId", required = false) Integer productId,
                                             @RequestParam(value = "productName", required = false) String productName,
                                             @RequestParam(value = "newUsers", required = false) Integer newUsers,
                                             @RequestParam(value = "amounts", required = false) Integer amounts,
                                             @RequestParam(value = "arup", required = false) Float arup,
                                             @RequestParam(value = "prodIdentification", required = false) String prodIdentification,
                                             @RequestParam(value = "userId", required = false) Integer userId,
                                             @RequestParam(value = "startDate", required = false) String startDate,
                                             @RequestParam(value = "endDate", required = false) String endDate,
                                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex) {
        Operator operator = (Operator) SessionUtils.getSessionAttribute("user");

        if (operator.getAccountType().equals("0")) {
            if (productId == null) {
               return queryBasicProductAnalysis();
            } else {
                if (productId != null) SessionUtils.saveSessionAttribute("productAnalysisId", productId);
                if (productName != null) SessionUtils.saveSessionAttribute("productAnalysisName", productName);
                if (newUsers != null) SessionUtils.saveSessionAttribute("productAnalysisNewUsers", newUsers);
                if (amounts != null) SessionUtils.saveSessionAttribute("productAnalysisAmounts", amounts);
                if (arup != null) SessionUtils.saveSessionAttribute("productAnalysisArup", arup);

                return query4Admin(productId, prodIdentification, userId, startDate, endDate, pageIndex);
            }
        } else {
            return query4Cooperation(productId, prodIdentification, startDate, endDate, pageIndex);
        }
    }

    @RequestMapping("queryBasicProductAnalysis.do")
    public ModelAndView queryBasicProductAnalysis() {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            List<BasicProAnalysis> basicProAnalysises = productAnalysisService.queryBasicProAnalysis();
            model.put("basicProAnalysises", basicProAnalysises);
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("queryBasicProductAnalysis exception:{}", ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "查询失败，请联系管理员！");
        }

        return new ModelAndView("basicProAnalysis", model);

    }


    public ModelAndView query4Admin(Integer productId, String prodIdentification,
                                    Integer userId, String startDate, String endDate, Integer pageIndex) {

        PageInfo pageInfo = new PageInfo();
        pageInfo.setStartPageIndex(pageIndex);
        Map<String, Object> model = new HashMap<String, Object>();


        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("productId", productId);
        params.put("prodIdentification", prodIdentification);
        params.put("userId", userId);
        params.put("startDate", startDate == null ? DateUtils.specifyDate(1) : startDate);
        params.put("endDate", endDate == null ? DateUtils.specifyDate(1) : endDate);

        try {
            pageInfo = productAnalysisService.queryProductAnalysis(pageInfo, params);
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("queryProductAnalysis exception:{}", ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "查询失败，请联系管理员！");
        }

        PageUtils.set(pageInfo, "productAnalysis.do", params);

        if (userId != null) {
            ProductIdentification identi = new ProductIdentification();

            identi.setProductId((Integer) SessionUtils.getSessionAttribute("productAnalysisId"));
            identi.setUserId(userId);

            model.put("prodIdentifications", productIdentificationService.queryProductIdentification(identi));
        }
        model.put("pageInfo", pageInfo);

        model.put("productId", productId);
        model.put("prodIdentification", prodIdentification);

        model.put("userId", userId);
        model.put("startDate", startDate == null ? DateUtils.specifyDate(1) : startDate);
        model.put("endDate", endDate == null ? DateUtils.specifyDate(1) : endDate);

        ProductIdentification userIdsIdenti = new ProductIdentification();
        userIdsIdenti.setProductId((Integer) SessionUtils.getSessionAttribute("productAnalysisId"));
        model.put("userIds", productIdentificationService.queryProductIdentification(userIdsIdenti));

        return new ModelAndView("productAnalysis4Admin", model);
    }


    public ModelAndView query4Cooperation(Integer productId, String prodIdentification,
                                          String startDate, String endDate, Integer pageIndex) {
        Operator operator = (Operator) SessionUtils.getSessionAttribute("user");
        int userId = operator.getId();

        PageInfo pageInfo = new PageInfo();

        pageInfo.setStartPageIndex(pageIndex);

        Map<String, Object> model = new HashMap<String, Object>();

        startDate = (startDate == null ? DateUtils.specifyDate(1) : startDate);
        endDate = (endDate == null ? DateUtils.specifyDate(1) : endDate);

        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("productId", productId);
        params.put("prodIdentification", prodIdentification);
        params.put("userId", userId);
        params.put("chargeoffStatus", Constant.ChargeOffStatus.FINISHED);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        try {
            pageInfo = productAnalysisService.queryProductAnalysis(pageInfo, params);
            List<ProductAnalysis> analysisList = pageInfo.getResult();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date yesterdayDate = simpleDateFormat.parse(DateUtils.specifyDate(1));
            Date parsedEndDate = simpleDateFormat.parse(endDate);

            List<ProductAnalysis> removeList = null;

            if (yesterdayDate.compareTo(parsedEndDate) < 0) {
                removeList = new ArrayList<ProductAnalysis>();
                for (ProductAnalysis analysis : analysisList) {
                    if (yesterdayDate.compareTo(analysis.getCreateTime()) < 0) {
                        String prodIdenti = analysis.getProdIdentification();
                        String prodIdentiKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + analysis.getProductId() + "_" + prodIdenti;
                        int chargeOffType = Integer.parseInt(CacheContainer.getUserChargeOffType(prodIdentiKey));

                        if (chargeOffType == Constant.ChargeOffType.DELAY) {
                            removeList.add(analysis);
                        }
                    }

                }
                analysisList.removeAll(removeList);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("queryProductAnalysis exception:{}", ExceptionUtils.getStackTrace(e));
            model.put("result", false);
            model.put("errorMsg", "查询失败，请联系管理员！");
        }

        PageUtils.set(pageInfo, "productAnalysis.do", params);


        model.put("pageInfo", pageInfo);

        model.put("productId", productId);
        model.put("prodIdentification", prodIdentification);
        model.put("userId", userId);
        model.put("startDate", startDate);
        model.put("endDate", endDate);

        HashMap<String, Object> prodParams = new HashMap<String, Object>();
        prodParams.put("userId", userId);
        model.put("products", productService.queryProduct4Cooperation(prodParams));

        if (productId != null) {
            ProductIdentification identi = new ProductIdentification();

            identi.setProductId(productId);
            identi.setUserId(userId);

            model.put("prodIdentifications", productIdentificationService.queryProductIdentification(identi));
        }

        return new ModelAndView("productAnalysis4Cooperation", model);
    }


    @ResponseBody
    @RequestMapping(value = "queryProAnalyChart.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String queryProAnalyChart(@RequestParam(value = "dateType", required = false) Integer dateType,
                                     @RequestParam(value = "timeType", required = false) Integer timeType) {
        Integer productId = (Integer) SessionUtils.getSessionAttribute("productAnalysisId");
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("productId", String.valueOf(productId));

        switch (timeType) {
            case 10:
                params.put("startDate", DateUtils.specifyDate(8));
                params.put("endDate", DateUtils.specifyDate(1));
                break;
            case 11:
                params.put("startDate", DateUtils.specifyDate(31));
                params.put("endDate", DateUtils.specifyDate(1));
                break;
        }

        try {
            List<BasicProAnalysis> basicProAnalysises = productAnalysisService.queryProAnalyByDay(params);

            if (basicProAnalysises == null || basicProAnalysises.size() == 0) {
                return "";
            }

            AnalyChart analyChart = new AnalyChart();

            ArrayList<HashMap<String, Object>> series = new ArrayList<HashMap<String, Object>>();
//            String result = "{\"series\":[{\"name\": \"Jane\",\"data\":[6,3,4]}, {\"name\":\"John\",\"data\":[5, 7, 3]}],\"yAxisText\":\"yuan\",\"categories\":[\"10-01\", \"10-02\", \"10-03\"]}";
            HashMap<String, Object> map = new HashMap<String, Object>();
            int[] datas = new int[basicProAnalysises.size()];
            String[] categories = new String[basicProAnalysises.size()];
            switch (dateType) {
                case 0:
                    map.put("name", "新增");


                    for (int i = 0; i < basicProAnalysises.size(); i++) {
                        datas[i] = basicProAnalysises.get(i).getTotalNewUsers();
                        categories[i] = basicProAnalysises.get(i).getCreateTime();
                    }
                    map.put("data", datas);
                    series.add(map);
                    analyChart.setSeries(series);
                    analyChart.setCategories(categories);
                    break;
                case 1:
                    map.put("name", "付费");


                    for (int i = 0; i < basicProAnalysises.size(); i++) {
                        datas[i] = basicProAnalysises.get(i).getTotalAmounts();
                        categories[i] = basicProAnalysises.get(i).getCreateTime();
                    }
                    map.put("data", datas);
                    series.add(map);
                    analyChart.setSeries(series);
                    analyChart.setCategories(categories);
                    break;
                case 2:
                    map.put("name", "arpu");

                    float[] floatDatas = new float[basicProAnalysises.size()];

                    for (int i = 0; i < basicProAnalysises.size(); i++) {
                        int totalAmounts = basicProAnalysises.get(i).getTotalAmounts();
                        int totalNewUsers = basicProAnalysises.get(i).getTotalNewUsers();

                        if (totalNewUsers == 0) {
                            floatDatas[i] = 0;
                        } else {
                            float arpu = new BigDecimal(totalAmounts).divide(new BigDecimal(totalNewUsers), 2, BigDecimal.ROUND_HALF_UP).floatValue();
                            floatDatas[i] = arpu;
                        }

                        categories[i] = basicProAnalysises.get(i).getCreateTime();
                    }
                    map.put("data", floatDatas);
                    series.add(map);
                    analyChart.setSeries(series);
                    analyChart.setCategories(categories);
                    break;
            }

            return JsonUtils.objectToJson(analyChart);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    @ResponseBody
    @RequestMapping(value = "productAnalysisEdit.do", method = RequestMethod.POST)
    public String editproductAnalysis(@RequestParam(value = "userDiscount", required = false) String userDiscount,
                                      @RequestParam(value = "amountsDiscount", required = false) String amountsDiscount,
                                      @RequestParam(value = "showNewUsers", required = false) String showNewUsers,
                                      @RequestParam(value = "newUsers", required = false) String newUsers,
                                      @RequestParam(value = "showOrderAmounts", required = false) String showOrderAmounts,
                                      @RequestParam(value = "editId", required = false) Integer id) {
        String result = null;

        ProductAnalysis analysis = new ProductAnalysis();

        analysis.setId(id);
        analysis.setAmountsDiscount(Float.parseFloat(amountsDiscount));
        analysis.setUsersDiscount(Float.parseFloat(userDiscount));
        analysis.setNewUsers(new BigDecimal(newUsers).intValue());
        analysis.setShowNewUsers(new BigDecimal(showNewUsers).intValue());
        analysis.setShowOrderAmounts(new BigDecimal(showOrderAmounts).intValue());


        try {
            productAnalysisService.editProductAnalysis(analysis);
            //成功
            result = "{\"result\":\"sucess\"}";
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("editproductAnalysis:{} fail exception:{}", analysis, ExceptionUtils.getStackTrace(e));
            result = "{\"result\":\"fail\"}";
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "productAnalysisDel.do", method = RequestMethod.POST)
    public String delProductAnalysis(@RequestParam(value = "delId", required = false) Integer id
    ) {
        String result = null;

        ProductAnalysis productAnalysis = null;

        try {
            productAnalysis = new ProductAnalysis();

            productAnalysis.setId(id);
            productAnalysisService.delProductAnalysis(productAnalysis);

            result = "{\"result\":\"sucess\"}";
        } catch (Exception e) {
            e.printStackTrace();
            Log.exception.warn("delProductAnalysis:{} fail exception:{}", productAnalysis, ExceptionUtils.getStackTrace(e));
            result = "{\"result\":\"fail\"}";
        }

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "queryProIdenti.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String queryProIdenti(@RequestParam(value = "userId", required = false) Integer userId,
                                 @RequestParam(value = "productId", required = false) Integer productId) {

        if (productId == null) productId = (Integer) SessionUtils.getSessionAttribute("productAnalysisId");
        if (userId == null) {
            Operator operator = (Operator) SessionUtils.getSessionAttribute("user");

            userId = operator.getId();
        }

        ProductIdentification identi = new ProductIdentification();

        identi.setProductId(productId);
        identi.setUserId(userId);
        List<ProductIdentification> productIdentifications = productIdentificationService.queryProIdentiGroup(identi);

        return JsonUtils.objectToJson(productIdentifications);
    }

    @ResponseBody
    @RequestMapping(value = "queryProIdentiGroup.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String queryProIdentiGroup(@RequestParam(value = "userId", required = false) Integer userId,
                                      @RequestParam(value = "productId", required = false) Integer productId) {
        ProductIdentification identi = new ProductIdentification();

        identi.setProductId(productId);
        if (userId != null) identi.setUserId(userId);
        List<ProductIdentification> productIdentifications = productIdentificationService.queryProIdentiGroup(identi);

        return JsonUtils.objectToJson(productIdentifications);
    }
}
