package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.dao.OrderDao;
import com.tianzh.admin.business.analysis.dao.ProductIdentificationDao;
import com.tianzh.admin.business.analysis.dao.UserDao;
import com.tianzh.admin.business.analysis.model.*;
import com.tianzh.admin.business.analysis.utils.DateUtils;
import com.tianzh.admin.common.page.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.Cache;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pig on 2015-06-05.
 */
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    UserService userService;
    @Autowired
    ProductIdentificationDao productIdentificationDao;

    @Autowired
    UserDao userDao;

    @Override
    public ThPayOrder findThOrder(Integer id) {
        return orderDao.findThOrder(id);
    }

    @Override
    public void addOrder(Order order) throws Exception {
        try {
            orderDao.addOrder(order);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Order> queryOrders(Order order, String startDate, String endDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("startDate", startDate);
        params.put("endDate", endDate);
        if (order != null) {
            params.put("productId", order.getProductId());
            params.put("prodIdentification", order.getProdIdentification());
            params.put("userId", order.getUserId());
            params.put("status", order.getStatus());
        }

        return orderDao.queryOrders(params);
    }

    @Override
    public List<ProductAnalysis> analysisOrders(Order order, String specifiedDate) throws Exception {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("specifiedDate", specifiedDate);

        if (order != null) {
            params.put("productId", order.getProductId());
            params.put("prodIdentification", order.getProdIdentification());
            params.put("userId", order.getUserId());
        }

        List<ProductIdentification> productIdentifications = productIdentificationDao.queryProductIdentification(params);

        List<ProductIdentification> channelIdentifications = new ArrayList<ProductIdentification>();
        List<ProductIdentification> cpIdentifications = new ArrayList<ProductIdentification>();
        //将渠道和CP分成两组
        for (ProductIdentification temp : productIdentifications) {
            if (temp.getAccountType().equals("1")) {
                //Cp
                cpIdentifications.add(temp);
            } else if (temp.getAccountType().equals("3")) {
                //渠道
                channelIdentifications.add(temp);
            }
        }

        //统计渠道数据
        List<ProductAnalysis> analysises = orderDao.analysisOrders(params);

        if (analysises == null && channelIdentifications != null && channelIdentifications.size() != 0) {
            analysises = new ArrayList<ProductAnalysis>();

            for (ProductIdentification identification : channelIdentifications) {
                ProductAnalysis analysis = new ProductAnalysis();

                analysis.setProductId(identification.getProductId());
                analysis.setProductName(identification.getProductName());
                analysis.setProdIdentification(identification.getProdIdentification());
                analysis.setUserId(identification.getUserId());
                analysis.setUserCompany(identification.getUserCompanyName());
                analysis.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDate));
                analysis.setNewUsers(0);

                analysises.add(analysis);
            }
        }

        if (analysises != null && channelIdentifications.size() > analysises.size()) {
            for (ProductIdentification identification : channelIdentifications) {
                if (!analysises.contains(identification)) {
                    ProductAnalysis analysis = new ProductAnalysis();

                    analysis.setNewUsers(0);
                    analysis.setProductId(identification.getProductId());
                    analysis.setProductName(identification.getProductName());
                    analysis.setProdIdentification(identification.getProdIdentification());
                    analysis.setUserId(identification.getUserId());
                    analysis.setUserCompany(identification.getUserCompanyName());
                    analysis.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDate));

                    analysises.add(analysis);
                }
            }

        }


        //String:productId,HashMap<String,ProductAnalysis>：String：prodIdentification,
        HashMap<String, HashMap<String, ProductAnalysis>> analysisGrouping = new HashMap<String, HashMap<String, ProductAnalysis>>();

        for (ProductAnalysis analy : analysises) {
            HashMap<String, ProductAnalysis> grouping = analysisGrouping.get(Constant.ReferenceKey.PRODUCTKEY + analy.getProductId());
            if (grouping == null) {
                grouping = new HashMap<String, ProductAnalysis>();

                grouping.put(analy.getProdIdentification(), analy);
                analysisGrouping.put(Constant.ReferenceKey.PRODUCTKEY + analy.getProductId(), grouping);
            } else {
                grouping.put(analy.getProdIdentification(), analy);
            }
        }

        HashMap<String, Integer> cpNewUsers = new HashMap<String, Integer>();
        HashMap<String, Integer> cpActiveUsers = new HashMap<String, Integer>();

        for (Map.Entry<String, HashMap<String, ProductAnalysis>> entry : analysisGrouping.entrySet()) {
            String productIdKey = entry.getKey();
            HashMap<String, ProductAnalysis> grouping = entry.getValue();

            //组装新增用户数
            ArrayList<UmengServiceImpl.ChannelUsers> channelUserses = (ArrayList<UmengServiceImpl.ChannelUsers>) userService.obtainNewUsers(productIdKey, specifiedDate);

            if (channelUserses != null && channelUserses.size() != 0) {
                int totalInstall = 0;
                int totalActive = 0;
                for (UmengServiceImpl.ChannelUsers channelUser : channelUserses) {
                    totalInstall += channelUser.getInstall();
                    totalActive += channelUser.getActive_user();

                    String prodIdentification = channelUser.getChannel();
                    if (prodIdentification.startsWith("\ufeff")) {
                        prodIdentification = prodIdentification.substring(prodIdentification.indexOf("\ufeff") + 1);
                    }

                    ProductAnalysis analysis = grouping.get(prodIdentification);
                    if (analysis != null) {
                        analysis.setNewUsers(analysis.getNewUsers() + channelUser.getInstall());
                        analysis.setActiveUsers(analysis.getActiveUsers() + channelUser.getActive_user());
                    }
                }

                cpNewUsers.put(productIdKey, totalInstall);
                cpActiveUsers.put(productIdKey, totalActive);
            }
        }

        for (ProductAnalysis analysis : analysises) {
            int newAmounts = queryNewAmounts(analysis, specifiedDate);
            analysis.setNewAmounts(newAmounts);

            //填充有效用户数据
            HashMap<String, Object> usersParams = new HashMap<String, Object>();

            usersParams.put("app_id", CacheContainer.getProductKey(Constant.ReferenceKey.PRODUCTKEY + analysis.getProductId()));
            usersParams.put("channel", analysis.getProdIdentification());
            usersParams.put("specifiedDate", specifiedDate);

            Integer useFullUsers = userDao.queryUseFullUsers(usersParams);
            useFullUsers = useFullUsers == null ? 0 : useFullUsers;
            analysis.setUsefullUsers(useFullUsers);

            //float userfullPercent;
            //float arpu;
            //float narpu;
            BigDecimal newUsersDec = new BigDecimal(analysis.getNewUsers());
            BigDecimal amountsDec = new BigDecimal(analysis.getOrderAmounts());
            BigDecimal newAmountsDec = new BigDecimal(analysis.getNewAmounts());
            BigDecimal useFullUsersDec = new BigDecimal(useFullUsers);

            BigDecimal userfullPercent;
            BigDecimal arpu;
            BigDecimal narpu;

            if (newUsersDec.intValue() != 0) {
                arpu = amountsDec.divide(newUsersDec, 2, BigDecimal.ROUND_HALF_UP);
                narpu = newAmountsDec.divide(newUsersDec, 2, BigDecimal.ROUND_HALF_UP);

                analysis.setArpu(Float.parseFloat(arpu.toString()));
                analysis.setNarpu(Float.parseFloat(narpu.toString()));
            }

            if (newUsersDec.intValue() != 0) {
                userfullPercent = useFullUsersDec.divide(newUsersDec, 2, BigDecimal.ROUND_HALF_UP);
                analysis.setUserfullPercent(Float.parseFloat(userfullPercent.toString()));
            }
        }

        //统计Cp数据
        for (ProductIdentification cpIdentifi : cpIdentifications) {
            HashMap<String, Object> cpParams = new HashMap<String, Object>();
            cpParams.put("specifiedDate", specifiedDate);
            cpParams.put("productId", cpIdentifi.getProductId());

            ProductAnalysis cpOrders = orderDao.analysisCpOrders(cpParams);

            if (cpOrders == null || cpOrders.getProductId() == null) {
                cpOrders = new ProductAnalysis();
                cpOrders.setProductId(cpIdentifi.getProductId());
                cpOrders.setProductName(cpIdentifi.getProductName());
                cpOrders.setCreateTime(DateUtils.formatStr(specifiedDate));
            }

            cpOrders.setUserId(cpIdentifi.getUserId());
            cpOrders.setUserCompany(cpIdentifi.getUserCompanyName());
            cpOrders.setProdIdentification("-/-");

            Integer newUsers = cpNewUsers.get(Constant.ReferenceKey.PRODUCTKEY + cpIdentifi.getProductId());
            cpOrders.setNewUsers(newUsers != null ? newUsers : 0);

            Integer activeUsers = cpActiveUsers.get(Constant.ReferenceKey.PRODUCTKEY + cpIdentifi.getProductId());
            cpOrders.setActiveUsers(activeUsers != null ? activeUsers : 0);

            int newAmounts = queryCpNewAmounts(cpOrders, specifiedDate);

            cpOrders.setNewAmounts(newAmounts);
            //填充有效用户数据
            HashMap<String, Object> usersParams = new HashMap<String, Object>();

            usersParams.put("app_id", CacheContainer.getProductKey(Constant.ReferenceKey.PRODUCTKEY + cpOrders.getProductId()));
            usersParams.put("specifiedDate", specifiedDate);

            Integer useFullUsers = userDao.queryUseFullUsers(usersParams);
            useFullUsers = useFullUsers == null ? 0 : useFullUsers;
            cpOrders.setUsefullUsers(useFullUsers);

            //float userfullPercent;
            //float arpu;
            //float narpu;
            BigDecimal newUsersDec = new BigDecimal(cpOrders.getNewUsers());
            BigDecimal amountsDec = new BigDecimal(cpOrders.getOrderAmounts());
            BigDecimal newAmountsDec = new BigDecimal(cpOrders.getNewAmounts());
            BigDecimal useFullUsersDec = new BigDecimal(useFullUsers);

            BigDecimal userfullPercent;
            BigDecimal arpu;
            BigDecimal narpu;

            if (newUsersDec.intValue() != 0) {
                arpu = amountsDec.divide(newUsersDec, 2, BigDecimal.ROUND_HALF_UP);
                narpu = newAmountsDec.divide(newUsersDec, 2, BigDecimal.ROUND_HALF_UP);

                cpOrders.setArpu(Float.parseFloat(arpu.toString()));
                cpOrders.setNarpu(Float.parseFloat(narpu.toString()));
            }

            if (newUsersDec.intValue() != 0) {
                userfullPercent = useFullUsersDec.divide(newUsersDec, 2, BigDecimal.ROUND_HALF_UP);
                cpOrders.setUserfullPercent(Float.parseFloat(userfullPercent.toString()));
            }

            analysises.add(cpOrders);
        }

        return analysises;
    }

    @Override
    public int queryNewAmounts(ProductAnalysis productAnalysis, String specifiedDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("specifiedDate", specifiedDate);

        if (productAnalysis != null) {
            params.put("productId", productAnalysis.getProductId());
            params.put("prodIdentification", productAnalysis.getProdIdentification());
            params.put("userId", productAnalysis.getUserId());
        }

        return orderDao.queryNewAmounts(params);
    }

    @Override
    public void updateOrder(Order order) throws Exception {
        try {
            orderDao.updateOrder(order);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateCpOrder(Order order) throws Exception {
        try {
            orderDao.updateCpOrder(order);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateChargeOrder(HashMap<String, Object> params) throws Exception {
        try {
            orderDao.updateChargeOrder(params);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PageInfo queryThOrders(PageInfo pageInfo, HashMap<String, Object> params) {
        HashMap<String, Object> newParams = new HashMap<String, Object>();

        newParams.put("pageInfo", pageInfo);
        String appId = (String) params.get("appId");
        newParams.put("appId", StringUtils.isNotEmpty(appId) ? appId.split("\\|")[1] : appId);
        newParams.put("channelId", params.get("channelId"));
        newParams.put("productId", params.get("productId"));
        newParams.put("payId", params.get("payId"));
        newParams.put("provinceId", params.get("provinceId"));
        newParams.put("statusCode", params.get("statusCode"));
        newParams.put("startDate", params.get("startDate"));
        newParams.put("endDate", params.get("endDate"));

        List<ThPayOrder> objects = orderDao.queryThOrders(newParams);

        pageInfo.setResult(objects);

        return pageInfo;
    }

    @Override
    public List<BasicProAnalysis> queryProAnalyByProduct(HashMap<String, String> params) {
        return orderDao.queryProAnalyByProduct(params);
    }


    @Override
    public List<SdkBasicAnalysis> queryBasicChargeAnalysises(HashMap<String, String> params) {
        List<SdkBasicAnalysis> analysises = new ArrayList<SdkBasicAnalysis>();

        try {
            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();

            String endDate = params.get("endDate");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedEndDate = simpleDateFormat.parse(endDate);
            String todayStr = simpleDateFormat.format(today);

            today = simpleDateFormat.parse(todayStr);
            //如果参数 Date 等于此 Date，则返回值 0；如果此 Date 在 Date 参数之前，则返回小于 0 的值；如果此 Date 在 Date 参数之后，则返回大于 0 的值。
            if (today.compareTo(parsedEndDate) == 0 || today.compareTo(parsedEndDate) < 0) {
                int day = cal.get(Calendar.DATE);
                cal.set(Calendar.DATE, day - 1);

                endDate = simpleDateFormat.format(cal.getTime());

                params.put("specifiedDate", todayStr);

                List<SdkBasicAnalysis> todayAnalysises = orderDao.analysisBasicChargeOrders(params);

                for (SdkBasicAnalysis analy : todayAnalysises) {
                    String productId = CacheContainer.getProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + analy.getProductKey());


                    String prodIdenKey;
                    String prodIdentification = analy.getProdIdentification();

                    if (prodIdentification.equals("-/-")) {
                        prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + "cp_" + analy.getUserId();
                    } else {
                        prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + prodIdentification;
                    }

                    String userId = CacheContainer.getUserId(prodIdenKey);
                    String userCompanyName = CacheContainer.getUserCompanyName(prodIdenKey);

                    analy.setUserId(Integer.parseInt(userId));
                    analy.setUserCompany(userCompanyName);

                    //新增用户数
                    int newUsers = 0;


                    String prodIdKey = Constant.ReferenceKey.PRODUCTKEY + productId;
                    ArrayList<UmengServiceImpl.ChannelUsers> channelUserses = (ArrayList<UmengServiceImpl.ChannelUsers>) userService.obtainNewUsers(prodIdKey, todayStr);

                    if (channelUserses != null && channelUserses.size() != 0) {

                        for (UmengServiceImpl.ChannelUsers channelUser : channelUserses) {

                            String channel = channelUser.getChannel();
                            if (channel.startsWith("\ufeff")) {
                                channel = channel.substring(channel.indexOf("\ufeff") + 1);
                            }

                            if (channel.equals(analy.getProdIdentification())) {
                                newUsers += channelUser.getInstall();
                            }
                        }
                    }

                    analy.setNewUsers(newUsers);

                    HashMap<String, String> sdkParams = new HashMap<String, String>();

                    sdkParams.put("payId", params.get("payId"));
                    sdkParams.put("productId", analy.getProductKey());
                    sdkParams.put("prodIdentification", analy.getProdIdentification());
                    sdkParams.put("specifiedDate", todayStr);

                    ArrayList<SdkProvinceAnalysis> sdkProvinceAnalysises = (ArrayList<SdkProvinceAnalysis>) orderDao.analysisBasicSdk(sdkParams);

                    analy.setProvinceAnalysis(sdkProvinceAnalysises);
                }

                analysises.addAll(todayAnalysises);
            }

            params.put("endDate", endDate);

            List<SdkBasicAnalysis> historySdkBasicAnalysises = orderDao.queryBasicChargeAnalysises(params);

            for (SdkBasicAnalysis analysis : historySdkBasicAnalysises) {
                HashMap<String, String> sdkParams = new HashMap<String, String>();

                sdkParams.put("payId", params.get("payId"));
                sdkParams.put("productId", analysis.getProductKey());
                sdkParams.put("prodIdentification", analysis.getProdIdentification());
                sdkParams.put("specifiedDate", DateUtils.date2Str(analysis.getCreateTime()));
                ArrayList<SdkProvinceAnalysis> sdkProvinceAnalysises = (ArrayList<SdkProvinceAnalysis>) orderDao.queryBasicSdkAnalysises(sdkParams);

                analysis.setProvinceAnalysis(sdkProvinceAnalysises);
            }

            analysises.addAll(historySdkBasicAnalysises);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return analysises;
    }

    @Override
    public List<SdkBasicAnalysis> analysisBasicChargeOrders(HashMap<String, String> params) {
        List<SdkBasicAnalysis> sdkBasicAnalysises = orderDao.analysisBasicChargeOrders(params);

        for (SdkBasicAnalysis analy : sdkBasicAnalysises) {
            String productId = CacheContainer.getProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + analy.getProductKey());


            String prodIdenKey;
            String prodIdentification = analy.getProdIdentification();

            if (prodIdentification.equals("-/-")) {
                prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + "cp_" + analy.getUserId();
            } else {
                prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + prodIdentification;
            }

            String userId = CacheContainer.getUserId(prodIdenKey);
            String userCompanyName = CacheContainer.getUserCompanyName(prodIdenKey);

            analy.setUserId(Integer.parseInt(userId));
            analy.setUserCompany(userCompanyName);
        }

        return sdkBasicAnalysises;
    }

    @Override
    public List<SdkProvinceAnalysis> analysisDetialSdk(HashMap<String, String> params) {
        ArrayList<SdkProvinceAnalysis> sdkProvinceAnalysises = (ArrayList<SdkProvinceAnalysis>) orderDao.analysisDetialSdk(params);

        for (SdkProvinceAnalysis analy : sdkProvinceAnalysises) {
            String productId = CacheContainer.getProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + analy.getProductKey());


            String providerName = analy.getProviderId() == 0 ? "中国移动" : analy.getProviderId() == 1 ? "中国联通" : "中国电信";
            analy.setProviderName(providerName);

            String prodIdenKey;
            String prodIdentification = analy.getProdIdentification();

            if (prodIdentification.equals("-/-")) {
                prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + "cp_" + analy.getUserId();
            } else {
                prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + prodIdentification;
            }

            String userId = CacheContainer.getUserId(prodIdenKey);
            String userCompanyName = CacheContainer.getUserCompanyName(prodIdenKey);

            analy.setUserId(Integer.parseInt(userId));
            analy.setUserCompany(userCompanyName);
        }

        return sdkProvinceAnalysises;
    }

    @Override
    public List<SdkBasicAnalysis> analysisBasicSdk(HashMap<String, String> params) {
        try {

            String specifiedDate = params.get("specifiedDate");

            List<SdkBasicAnalysis> sdkBasicAnalysis = orderDao.analysisBasicChargeOrders(params);

            for (SdkBasicAnalysis analy : sdkBasicAnalysis) {
                String productId = CacheContainer.getProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + analy.getProductKey());

                String prodIdenKey;
                String prodIdentification = analy.getProdIdentification();

                if (prodIdentification.equals("-/-")) {
                    prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + "cp_" + analy.getUserId();
                } else {
                    prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productId + "_" + prodIdentification;
                }

                String userId = CacheContainer.getUserId(prodIdenKey);
                String userCompanyName = CacheContainer.getUserCompanyName(prodIdenKey);

                analy.setUserId(Integer.parseInt(userId));
                analy.setUserCompany(userCompanyName);

                //新增用户数
                int newUsers = 0;

                String prodIdKey = Constant.ReferenceKey.PRODUCTKEY + productId;
                ArrayList<UmengServiceImpl.ChannelUsers> channelUserses = (ArrayList<UmengServiceImpl.ChannelUsers>) userService.obtainNewUsers(prodIdKey, specifiedDate);

                if (channelUserses != null && channelUserses.size() != 0) {

                    for (UmengServiceImpl.ChannelUsers channelUser : channelUserses) {

                        String channel = channelUser.getChannel();
                        if (channel.startsWith("\ufeff")) {
                            channel = channel.substring(channel.indexOf("\ufeff") + 1);
                        }

                        if (channel.equals(analy.getProdIdentification())) {
                            newUsers += channelUser.getInstall();
                        }
                    }
                }

                analy.setNewUsers(newUsers);
            }

            return sdkBasicAnalysis;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    @Override
    public void addSdkBasicAnalysises(ArrayList<SdkBasicAnalysis> SdkBasicAnalysises) throws Exception {
        orderDao.addSdkBasicAnalysises(SdkBasicAnalysises);
    }

    @Override
    public void addSdkProvinceAnalysises(ArrayList<SdkProvinceAnalysis> sdkProvinceAnalysises) throws Exception {
        orderDao.addSdkProvinceAnalysises(sdkProvinceAnalysises);
    }

    @Override
    public ArrayList<SdkProvinceAnalysis> queryBasicProvinceAnalysis(HashMap<String, Object> params) {
        try {
            String specifiedDate = (String) params.get("specifiedDate");

            Calendar cal = Calendar.getInstance();
            Date today = cal.getTime();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedEndDate = simpleDateFormat.parse(specifiedDate);
            String todayStr = simpleDateFormat.format(today);

            today = simpleDateFormat.parse(todayStr);
            //如果参数 Date 等于此 Date，则返回值 0；如果此 Date 在 Date 参数之前，则返回小于 0 的值；如果此 Date 在 Date 参数之后，则返回大于 0 的值。
            if (today.compareTo(parsedEndDate) == 0 || today.compareTo(parsedEndDate) < 0) {
                //查询今天的数据
                return orderDao.analysisBasicProvinceAnalysis(params);
            } else {
                //查询数据库
                return orderDao.queryBasicProvinceAnalysis(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public int queryCpNewAmounts(ProductAnalysis productAnalysis, String specifiedDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("specifiedDate", specifiedDate);

        if (productAnalysis != null) {
            params.put("productId", productAnalysis.getProductId());
        }

        return orderDao.queryNewAmounts(params);
    }
}
