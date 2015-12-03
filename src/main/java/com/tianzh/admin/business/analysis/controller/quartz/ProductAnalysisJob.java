package com.tianzh.admin.business.analysis.controller.quartz;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.*;
import com.tianzh.admin.business.analysis.service.*;
import com.tianzh.admin.business.analysis.utils.DateUtils;
import com.tianzh.admin.common.util.encrypt.md5.Md5Utils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pig on 2015-06-07.
 */
@Component("productAnalysisJob")
public class ProductAnalysisJob {
    @Autowired
    OrderService orderService;

    @Autowired
    ProductAnalysisService productAnalysisService;

    @Autowired
    HttpRequestService httpRequestService;

    @Autowired
    ProductIdentificationService productIdentificationService;

    @Autowired
    private UserService userService;

    //每天凌晨1点统计前一天的数据
    public void schedule() {
        productAnalysisJob();

        sdkAnalysisJob();
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

                    orderService.addSdkBasicAnalysises(sdkBasicAnalysises);
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
                        orderService.addSdkProvinceAnalysises(sdkProvinceAnalysises);
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

                productAnalysisService.addProductAnalysis(analysises);
            }
            //记录日志信息
            Log.productAnalysisJob.info("ProductAnalysisJob Run at:{} sucess add analysises:{}", new Date().toLocaleString(), analysises);
        } catch (Exception e) {
            e.printStackTrace();
            //记录日志信息
            Log.productAnalysisJob.error("ProductAnalysisJob Run at:{} failed add analysises:{} exception:{}", new Object[]{new Date().toLocaleString(), analysises, ExceptionUtils.getStackTrace(e)});
        }
    }

    private void saveZhiFuPayData() throws IOException {
        String url = "http://202.107.192.23:6821/chn-data/service/cpsettle.do";
        String merchantId = "SZJBXLPAY1001";
        String key = "K@A@*+NXxY+@*#b-";
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(url).append("?").append("merchantId=").append(merchantId).append("&").append("startDate=")
                .append(DateUtils.specifyDate(1)).append("&").append("endDate=").append(DateUtils.specifyDate(1))
                .append("&signMsg=").append(Md5Utils.getMD5(("merchantId=" + merchantId + "&startDate=" + DateUtils.specifyDate(1) + "&endDate=" + DateUtils.specifyDate(1) + "&key=" + key).getBytes()));

        System.out.println(httpRequestService.get(url));
    }
}
