package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.dao.ProductAnalysisDao;
import com.tianzh.admin.business.analysis.model.*;
import com.tianzh.admin.business.analysis.utils.DateUtils;
import com.tianzh.admin.common.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pig on 2015-06-06.
 */
@Component
public class ProductAnalysisServiceImpl implements ProductAnalysisService {
    @Autowired
    ProductAnalysisDao productAnalysisDao;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    ProductDetialService productDetialService;
    @Autowired
    ProductService productService;

    @Override
    public void addProductAnalysis(List<ProductAnalysis> productAnalysises) throws Exception {
        try {
            productAnalysisDao.addProductAnalysis(productAnalysises);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void editProductAnalysis(ProductAnalysis analysis) throws Exception {
        try {
            productAnalysisDao.editProductAnalysis(analysis);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delProductAnalysis(ProductAnalysis analysis) throws Exception {
        try {
            productAnalysisDao.delProductAnalysis(analysis);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateProductAnalysisAmount(ProductAnalysis analysis) throws Exception {
        try {
            this.productAnalysisDao.updateProductAnalysisAmount(analysis);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateSdkBasicAnalysis(SdkBasicAnalysis analysis) throws Exception {
        try {
            this.productAnalysisDao.updateSdkBasicAnalysis(analysis);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateSdkProvinceAnalysis(SdkProvinceAnalysis analysis) throws Exception {
        try {
            analysis.setId(productAnalysisDao.findSdkProvinceAnalysisId(analysis));
            this.productAnalysisDao.updateSdkProvinceAnalysis(analysis);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PageInfo queryProductAnalysis(PageInfo pageInfo, HashMap<String, Object> params) throws Exception {
        params.put("pageInfo", pageInfo);

        ProductAnalysis analysis = null;

        if (params.get("productId") != null) {
            analysis = new ProductAnalysis();
            analysis.setProductId((Integer) params.get("productId"));
        }

        if (params.get("prodIdentification") != null) {
            if (analysis == null) analysis = new ProductAnalysis();
            analysis.setProdIdentification((String) params.get("prodIdentification"));
        }

        if (params.get("userId") != null) {
            if (analysis == null) analysis = new ProductAnalysis();

            analysis.setUserId((Integer) params.get("userId"));
        }

        if (params.get("chargeoffStatus") != null) {
            if (analysis == null) analysis = new ProductAnalysis();

            analysis.setChargeOffStatus((Integer) params.get("chargeoffStatus"));
        }

        List<ProductAnalysis> analysises = queryProductAnalysis(analysis, (String) params.get("startDate"), (String) params.get("endDate"));

        pageInfo.setResult(analysises);

        return pageInfo;
    }

    @Override
    public List<ProductAnalysis> queryProductAnalysis(ProductAnalysis analysis, String startDate, String endDate) throws Exception {
        HashMap<String, Object> params = new HashMap<String, Object>();
        List<ProductAnalysis> analysises = new ArrayList<ProductAnalysis>();

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedEndDate = simpleDateFormat.parse(endDate);
        String todayStr = simpleDateFormat.format(today);

        today = simpleDateFormat.parse(todayStr);
        //如果参数 Date 等于此 Date，则返回值 0；如果此 Date 在 Date 参数之前，则返回小于 0 的值；如果此 Date 在 Date 参数之后，则返回大于 0 的值。
        if (today.compareTo(parsedEndDate) == 0 || today.compareTo(parsedEndDate) < 0) {
            int day = cal.get(Calendar.DATE);
            cal.set(Calendar.DATE, day - 1);

            endDate = simpleDateFormat.format(cal.getTime());

            Order orderParam = null;

            if (analysis != null) {
                orderParam = new Order();
                if ((Integer) analysis.getProductId() != null) {
                    orderParam.setProductId(analysis.getProductId());
                }
                if (analysis.getProdIdentification() != null) {
                    orderParam.setProdIdentification(analysis.getProdIdentification());
                }
                if ((Integer) analysis.getUserId() != null) {
                    orderParam.setUserId(analysis.getUserId());
                }
            }
            List<ProductAnalysis> todayAnalysis = orderService.analysisOrders(orderParam, simpleDateFormat.format(today));

            for (ProductAnalysis analy : todayAnalysis) {
                String prodIdenKey;
                String prodIdentification = analy.getProdIdentification();

                if (prodIdentification.equals("-/-")) {
                    prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + analy.getProductId() + "_" + "cp_" + analy.getUserId();
                } else {
                    prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + analy.getProductId() + "_" + prodIdentification;
                }
                String cooperationType = CacheContainer.getUserCooperationType(prodIdenKey);
                String sharingStr = CacheContainer.getSharing(prodIdenKey);
                String chargeOffType = CacheContainer.getUserChargeOffType(prodIdenKey);
                String discountStr = CacheContainer.getUserDiscount(prodIdenKey);

                analy.setAmountsDiscount(Float.parseFloat(discountStr));
                analy.setCooperationType(Integer.parseInt(cooperationType));
                analy.setSharing(Float.parseFloat(sharingStr));
                analy.setChargeOffType(Integer.parseInt(chargeOffType));
            }

            analysises.addAll(todayAnalysis);
        }

        params.put("startDate", startDate);
        params.put("endDate", endDate);

        if (analysis != null) {
            params.put("productId", analysis.getProductId());
            params.put("prodIdentification", analysis.getProdIdentification());
            params.put("userId", analysis.getUserId());

            if (analysis.getChargeOffStatus() == 1) {
                params.put("offStatus", analysis.getChargeOffStatus());
            }
        }

        analysises.addAll(productAnalysisDao.queryProductAnalysis(params));

        return analysises;
    }

    @Override
    public List<BasicProAnalysis> queryBasicProAnalysis() throws Exception {
        ArrayList<BasicProAnalysis> results = new ArrayList<BasicProAnalysis>();

        List<Product> products = productService.queryProduct(null);
        HashMap<String, String> params = new HashMap<String, String>();

        for (Product pro : products) {
            params.put("productId", String.valueOf(pro.getId()));
            params.put("startDate", DateUtils.getFirstDay());
            params.put("endDate", DateUtils.specifyDate(1));
            //查询月初到昨天累计用户和累计充值
            List<BasicProAnalysis> total = productAnalysisDao.queryProAnalyByProduct(params);
            //查询今天的充值
            params.put("startDate", DateUtils.specifyDate(0));
            params.put("endDate", DateUtils.specifyDate(0));
            List<BasicProAnalysis> today = orderService.queryProAnalyByProduct(params);

            BasicProAnalysis result = new BasicProAnalysis();

            if (today != null && today.size() != 0) {
                result.setAmounts(today.get(0).getAmounts());
                result.setTotalAmounts(today.get(0).getAmounts());
            } else {
                result.setAmounts(0);
                result.setTotalAmounts(0);
            }

            if (total != null && total.size() != 0) {
                result.setTotalAmounts(result.getAmounts() + total.get(0).getTotalAmounts());
                result.setTotalNewUsers(total.get(0).getTotalNewUsers());
            } else {
                result.setTotalAmounts(0);
                result.setTotalNewUsers(0);
            }


            results.add(result);
            result.setAppId(pro.getId());
            result.setAppName(pro.getName());
        }

        if (results.size() == 0) {
            return results;
        }

        HashMap<String, Object> detialParams = new HashMap<String, Object>();
        detialParams.put("keyType", Constant.KeyType.UMENGKEY);

        List<ProductDetial> productDetials = productDetialService.queryProductDetials(detialParams);

        HashMap<String, String> umengKeyMap = new HashMap<String, String>();

        for (ProductDetial detial : productDetials) {
            umengKeyMap.put(Constant.ReferenceKey.PRODUCTKEY + detial.getProductId(), detial.getThirdPartyKey());
        }

        //填充今日新增和arup
        for (BasicProAnalysis analysis : results) {
            String productKey = Constant.ReferenceKey.PRODUCTKEY + analysis.getAppId();
            UmengServiceImpl.App app = userService.obtainAppNewUsers(productKey, umengKeyMap.get(productKey), DateUtils.specifyDate(0));

            Integer newUsers = (app != null ? app.getNew_users() : 0);

            analysis.setNewUsers(newUsers);
            analysis.setTotalNewUsers(analysis.getTotalNewUsers() + newUsers);
            if (newUsers != 0) {
                float arpu = new BigDecimal(analysis.getAmounts()).divide(new BigDecimal(newUsers), 2, BigDecimal.ROUND_HALF_UP).floatValue();
                analysis.setArup(arpu);
            }
        }

        return results;
    }

    @Override
    public List<BasicProAnalysis> queryProAnalyByDay(HashMap<String, String> params) throws Exception {
        return productAnalysisDao.queryProAnalyByDay(params);
    }


}
