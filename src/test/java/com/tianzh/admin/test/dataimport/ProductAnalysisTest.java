package com.tianzh.admin.test.dataimport;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.controller.servlet.InitServlet;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.*;
import com.tianzh.admin.business.analysis.service.*;
import com.tianzh.admin.business.analysis.utils.DateUtils;
import com.tianzh.admin.common.util.encrypt.md5.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pig on 2015-06-06.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:web/WEB-INF/applicationContext.xml"})
public class ProductAnalysisTest {

    @Autowired
    ProductAnalysisService productAnalysisService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductIdentificationService productIdentificationService;

    @Autowired
    InitServlet initServlet;

    @Autowired
    ProductDetialService productDetialService;

    @Autowired
    private UserService userService;

    @Test
    public void addProductAnalysisTest() {
        try {
            initServlet.init(null);
            sdkAnalysisJob();
            productAnalysisJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sync(String specifyDate) {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("keyType", Constant.KeyType.TPPKEY2);
            List<ProductDetial> productDetials = productDetialService.queryProductDetials(params);

            for (ProductDetial detial : productDetials) {
                String thirdPartyKey = detial.getThirdPartyKey();

                String merchantId = thirdPartyKey.split(",")[0];
                String key = thirdPartyKey.split(",")[1];
                String letuAppId = thirdPartyKey.split(",")[2];

                StringBuffer stringBuffer = new StringBuffer();
                String md5String = Md5Utils.getMD5(("merchantId=" + merchantId + "&startDate=" + specifyDate + "&endDate=" + specifyDate + "&key=" + key).getBytes());
                stringBuffer.append("http://202.107.192.23:6821/chn-data/service/cpsettle.do").append("?").append("merchantId=").append(merchantId).append("&").append("startDate=").append(specifyDate).append("&").append("endDate=").append(specifyDate).append("&signMsg=").append(md5String);
                String responseStr = httpRequestService.get(stringBuffer.toString());

                if (StringUtils.isNotEmpty(responseStr)) {
                    String[] resStrs = responseStr.split("\\n");
                    HashMap<String, Integer> incomes = new HashMap<String, Integer>();
                    HashMap<String, Integer> providerIncomes = new HashMap<String, Integer>();

                    //resStrs[i].split(",")[4]; 运营商信息 0：移动 1：联通 2：电信
                    for (int i = 0; i < resStrs.length; i++) {
                        String appId = resStrs[i].split(",")[1];

                        if (appId.equals(letuAppId)) {
                            String channel = resStrs[i].split(",")[3];
                            String income = resStrs[i].split(",")[5];
                            String provider = resStrs[i].split(",")[4];

                            providerIncomes.put(channel + "_" + provider, Integer.valueOf(income));

                            Integer totalIncome = incomes.get(channel) == null ? 0 : incomes.get(channel);
                            totalIncome = totalIncome + Integer.parseInt(income);
                            incomes.put(channel, totalIncome);

                        } else {
                            continue;
                        }
                    }

                    if (incomes.size() != 0 && providerIncomes.size() != 0) {

                        String productKey = CacheContainer.getProductKey(Constant.ReferenceKey.PRODUCTKEY + detial.getProductId());

                        for (Map.Entry<String, Integer> entry : providerIncomes.entrySet()) {
                            String channel_provider = entry.getKey();
                            String channel = channel_provider.split("_")[0];
                            String provider = channel_provider.split("_")[1];

                            Integer income = entry.getValue();

                            //更新省份数据分析
                            //String productKey;String prodIdentification;int providerId;int sucessAmounts;
                            SdkProvinceAnalysis sdkProvinceAnalysis = new SdkProvinceAnalysis();

                            sdkProvinceAnalysis.setProductKey(productKey);
                            sdkProvinceAnalysis.setProdIdentification(channel);
                            sdkProvinceAnalysis.setProviderId(Integer.parseInt(provider));
                            sdkProvinceAnalysis.setSucessAmounts(income);
                            sdkProvinceAnalysis.setPayId(1);
                            sdkProvinceAnalysis.setSyncDate(specifyDate);

                            productAnalysisService.updateSdkProvinceAnalysis(sdkProvinceAnalysis);
                        }


                        for (Map.Entry<String, Integer> entry : incomes.entrySet()) {
                            String channel = entry.getKey();
                            Integer income = entry.getValue();

                            ProductAnalysis analysis = new ProductAnalysis();

                            analysis.setProductId(detial.getProductId());
                            analysis.setProdIdentification(channel);
                            analysis.setOrderAmounts(income);
                            analysis.setSyncDate(specifyDate);
                            //更新渠道账单
                            productAnalysisService.updateProductAnalysisAmount(analysis);
                            //更新Cp账单
                            analysis.setProdIdentification("-/-");
                            productAnalysisService.updateProductAnalysisAmount(analysis);

                            //更新基础支付分析
                            //String productKey;prodIdentification;sucessAmounts;
                            SdkBasicAnalysis sdkBasicAnalysis = new SdkBasicAnalysis();

                            sdkBasicAnalysis.setProductKey(productKey);
                            sdkBasicAnalysis.setProdIdentification(channel);
                            sdkBasicAnalysis.setSucessAmounts(income);
                            sdkBasicAnalysis.setSyncDate(specifyDate);

                            productAnalysisService.updateSdkBasicAnalysis(sdkBasicAnalysis);

                            Log.productAnalysisJob.info("LeTuSyncJob Run at:{} sucess update analysis:{}", specifyDate, analysis);
                        }
                    }
                } else {
                    Log.productAnalysisJob.info("LeTuSyncJob Run at:{} failed update analysis response nothing ", specifyDate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.productAnalysisJob.info("LeTuSyncJob Run at:{} failed add analysis exception:{}", new Object[]{new Date().toLocaleString(), ExceptionUtils.getStackTrace(e)});
        }
    }

    private void sdkAnalysisJob() {
        String specifyDate = DateUtils.specifyDate(1);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("specifiedDate", specifyDate);

        List<ProductIdentification> productIdentifications = productIdentificationService.queryAllProIdentiGroup();

        for (ProductIdentification identification : productIdentifications) {
            String productKey = CacheContainer.getProductKey(Constant.ReferenceKey.PRODUCTKEY + identification.getProductId());
            params.put("productId", productKey);
            params.put("prodIdentification", identification.getProdIdentification());

            ArrayList<SdkBasicAnalysis> sdkBasicAnalysises = (ArrayList<SdkBasicAnalysis>) orderService.analysisBasicChargeOrders(params);

            if (sdkBasicAnalysises != null && sdkBasicAnalysises.size() != 0) {
                try {
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

                        //新增用户数
                        int newUsers = 0;


                        String prodIdKey = Constant.ReferenceKey.PRODUCTKEY + productId;
                        ArrayList<UmengServiceImpl.ChannelUsers> channelUserses = (ArrayList<UmengServiceImpl.ChannelUsers>) userService.obtainNewUsers(prodIdKey, specifyDate);

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

//                    orderService.addSdkBasicAnalysises(sdkBasicAnalysises);
                    Log.productAnalysisJob.info("sdkAnalysisJob Run at:{} sucess add sdkBasicAnalysises:{}", new Date().toLocaleString(), sdkBasicAnalysises);
                } catch (Exception e) {
                    e.printStackTrace();
                    //记录日志
                    Log.productAnalysisJob.error("sdkAnalysisJob Run at:{} failed add sdkBasicAnalysises:{} exception:{}", new Object[]{new Date().toLocaleString(), sdkBasicAnalysises, ExceptionUtils.getStackTrace(e)});
                    continue;
                }

                ArrayList<SdkProvinceAnalysis> sdkProvinceAnalysises = (ArrayList<SdkProvinceAnalysis>) orderService.analysisDetialSdk(params);

                if (sdkProvinceAnalysises != null && sdkProvinceAnalysises.size() != 0) {
                    try {
//                        orderService.addSdkProvinceAnalysises(sdkProvinceAnalysises);
                        Log.productAnalysisJob.info("sdkAnalysisJob Run at:{} sucess add sdkProvinceAnalysises:{}", new Date().toLocaleString(), sdkProvinceAnalysises);

                    } catch (Exception e) {
                        e.printStackTrace();
                        //记录日志
                        Log.productAnalysisJob.error("sdkAnalysisJob Run at:{} failed add sdkProvinceAnalysises:{} exception:{}", new Object[]{new Date().toLocaleString(), sdkProvinceAnalysises, ExceptionUtils.getStackTrace(e)});
                        continue;
                    }
                }
            }

        }

    }


    private void productAnalysisJob() {
        List<ProductAnalysis> analysises = null;

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        cal.set(Calendar.DATE, day - 1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String specifiedDate = simpleDateFormat.format(cal.getTime());

        try {
            analysises = orderService.analysisOrders(null, specifiedDate);

            if (analysises != null && analysises.size() != 0) {

                for (ProductAnalysis analy : analysises) {

                    String prodIdenKey;
                    String prodIdentification = analy.getProdIdentification();

                    if (prodIdentification.equals("-/-")) {
                        prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + analy.getProductId() + "_" + "cp_" + analy.getUserId();
                    } else {
                        prodIdenKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + analy.getProductId() + "_" + prodIdentification;
                    }

                    int cooperationType = Integer.parseInt(CacheContainer.getUserCooperationType(prodIdenKey));
                    float discount = Float.parseFloat(CacheContainer.getUserDiscount(prodIdenKey));
                    float sharing = Float.parseFloat(CacheContainer.getSharing(prodIdenKey));
                    int chargeOffType = Integer.parseInt(CacheContainer.getUserChargeOffType(prodIdenKey));


                    analy.setAmountsDiscount(discount);
                    analy.setCooperationType(cooperationType);
                    analy.setSharing(sharing);
                    analy.setChargeOffType(chargeOffType);

                    if (chargeOffType == Constant.ChargeOffType.REALTIME) {
                        if (cooperationType == Constant.CooperationType.ACooperation) {
                            int newUsers = analy.getNewUsers();
                            analy.setShowNewUsers((int) (newUsers * (1 - discount)));
                            analy.setChargeOffStatus(Constant.ChargeOffStatus.FINISHED);
                        }

                        if (cooperationType == Constant.CooperationType.SCooperation) {
                            int orderAmounts = analy.getOrderAmounts();
                            int newUsers = analy.getNewUsers();

                            analy.setShowNewUsers((int) (newUsers * (1 - discount)));
                            analy.setShowOrderAmounts((int) (orderAmounts * (1 - discount)));
                            analy.setChargeOffStatus(Constant.ChargeOffStatus.FINISHED);
                        }
                    }
                }

//                productAnalysisService.addProductAnalysis(analysises);
            }
            //记录日志信息
            Log.productAnalysisJob.info("ProductAnalysisJob Run at:{} sucess add analysises:{}", new Date().toLocaleString(), analysises);
        } catch (Exception e) {
            e.printStackTrace();
            //记录日志信息
            Log.productAnalysisJob.error("ProductAnalysisJob Run at:{} failed add analysises:{} exception:{}", new Object[]{new Date().toLocaleString(), analysises, ExceptionUtils.getStackTrace(e)});
        }
    }

    @Test
    public void queryProductAnalysisTest() {
        try {
            List<ProductAnalysis> analysises = productAnalysisService.queryProductAnalysis(null, "2015-06-01", "2015-06-15");
            System.out.println("查询结果:" + analysises);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("查询失败");

        }
    }

    @Test
    public void editProductAnalysisTest() {

        ProductAnalysis analysis = new ProductAnalysis();


        try {
            productAnalysisService.editProductAnalysis(analysis);
            System.out.println("修改成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("修改失败！！！");
        }
    }


    @Test
    public void deleteProductTest() {
        ProductAnalysis analysis = new ProductAnalysis();

        analysis.setId(1);
        try {
            productAnalysisService.delProductAnalysis(analysis);
            System.out.println("删除成功！！！");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("修改失败！！！");

        }
    }

    @Autowired
    HttpRequestService httpRequestService;

    @Test
    public void testLetuSync() {
        String url = "http://202.107.192.23:6821/chn-data/service/cpsettle.do";

        try {
            initServlet.init(null);

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("keyType", Constant.KeyType.TPPKEY2);
            List<ProductDetial> productDetials = productDetialService.queryProductDetials(params);

            for (ProductDetial detial : productDetials) {
                String thirdPartyKey = detial.getThirdPartyKey();

                String merchantId = thirdPartyKey.split(",")[0];
                String key = thirdPartyKey.split(",")[1];
                String letuAppId = thirdPartyKey.split(",")[2];

                StringBuffer stringBuffer = new StringBuffer();
                String md5String = Md5Utils.getMD5(("merchantId=" + merchantId + "&startDate=" + DateUtils.specifyDate(1) + "&endDate=" + DateUtils.specifyDate(1) + "&key=" + key).getBytes());
                stringBuffer.append(url).append("?").append("merchantId=").append(merchantId).append("&").append("startDate=").append(DateUtils.specifyDate(1)).append("&").append("endDate=").append(DateUtils.specifyDate(1)).append("&signMsg=").append(md5String);
                String responseStr = httpRequestService.get(stringBuffer.toString());

                if (StringUtils.isNotEmpty(responseStr)) {
                    String[] resStrs = responseStr.split("\\n");
                    HashMap<String, Integer> incomes = new HashMap<String, Integer>();
                    HashMap<String, Integer> providerIncomes = new HashMap<String, Integer>();

                    //resStrs[i].split(",")[4]; 运营商信息 0：移动 1：联通 2：电信
                    for (int i = 0; i < resStrs.length; i++) {
                        String appId = resStrs[i].split(",")[1];

                        if (appId.equals(letuAppId)) {
                            String channel = resStrs[i].split(",")[3];
                            String income = resStrs[i].split(",")[5];
                            String provider = resStrs[i].split(",")[4];

                            providerIncomes.put(channel + "_" + provider, Integer.valueOf(income));

                            Integer totalIncome = incomes.get(channel) == null ? 0 : incomes.get(channel);
                            totalIncome = totalIncome + Integer.parseInt(income);
                            incomes.put(channel, totalIncome);

                        } else {
                            continue;
                        }
                    }

                    if (incomes.size() != 0 && providerIncomes.size() != 0) {

                        String productKey = CacheContainer.getProductKey(Constant.ReferenceKey.PRODUCTKEY + detial.getProductId());

                        for (Map.Entry<String, Integer> entry : providerIncomes.entrySet()) {
                            String channel_provider = entry.getKey();
                            String channel = channel_provider.split("_")[0];
                            String provider = channel_provider.split("_")[1];

                            Integer income = entry.getValue();

                            //更新省份数据分析
                            //String productKey;String prodIdentification;int providerId;int sucessAmounts;
                            SdkProvinceAnalysis sdkProvinceAnalysis = new SdkProvinceAnalysis();

                            sdkProvinceAnalysis.setProductKey(productKey);
                            sdkProvinceAnalysis.setProdIdentification(channel);
                            sdkProvinceAnalysis.setProviderId(Integer.parseInt(provider));
                            sdkProvinceAnalysis.setSucessAmounts(income);
                            sdkProvinceAnalysis.setPayId(1);
                            sdkProvinceAnalysis.setSyncDate(DateUtils.specifyDate(1));

                            productAnalysisService.updateSdkProvinceAnalysis(sdkProvinceAnalysis);
                        }


//                        for (Map.Entry<String, Integer> entry : incomes.entrySet()) {
//                            String channel = entry.getKey();
//                            Integer income = entry.getValue();
//
//                            ProductAnalysis analysis = new ProductAnalysis();
//
//                            analysis.setProductId(detial.getProductId());
//                            analysis.setProdIdentification(channel);
//                            analysis.setOrderAmounts(income);
//                            analysis.setSyncDate(DateUtils.specifyDate(1));
//                            //更新渠道账单
//                            productAnalysisService.updateProductAnalysisAmount(analysis);
//                            //更新Cp账单
//                            analysis.setProdIdentification("-/-");
//                            productAnalysisService.updateProductAnalysisAmount(analysis);
//
//                            //更新基础支付分析
//                            //String productKey;prodIdentification;sucessAmounts;
//                            SdkBasicAnalysis sdkBasicAnalysis = new SdkBasicAnalysis();
//
//                            sdkBasicAnalysis.setProductKey(productKey);
//                            sdkBasicAnalysis.setProdIdentification(channel);
//                            sdkBasicAnalysis.setSucessAmounts(income);
//                            sdkBasicAnalysis.setSyncDate(DateUtils.specifyDate(1));
//
//                            productAnalysisService.updateSdkBasicAnalysis(sdkBasicAnalysis);
//
//                            Log.productAnalysisJob.info("LeTuSyncJob Run at:{} sucess update analysis:{}", new Date().toLocaleString(), analysis);
//                        }
                    }
                } else {
                    Log.productAnalysisJob.info("LeTuSyncJob Run at:{} failed update analysis response nothing ", new Date().toLocaleString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.productAnalysisJob.info("LeTuSyncJob Run at:{} failed add analysis exception:{}", new Object[]{new Date().toLocaleString(), ExceptionUtils.getStackTrace(e)});
        }
    }
}
