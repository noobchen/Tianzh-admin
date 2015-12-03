package com.tianzh.admin.business.analysis.controller.quartz;


import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.*;
import com.tianzh.admin.business.analysis.service.HttpRequestService;
import com.tianzh.admin.business.analysis.service.OrderService;
import com.tianzh.admin.business.analysis.service.ProductAnalysisService;
import com.tianzh.admin.business.analysis.service.ProductDetialService;
import com.tianzh.admin.business.analysis.utils.DateUtils;
import com.tianzh.admin.common.util.encrypt.md5.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pig on 2015-08-24.
 */
@Component("leTuSyncJob")
public class LeTuSyncJob {
    private String url = "http://202.107.192.23:6821/chn-data/service/cpsettle.do";

    @Autowired
    HttpRequestService httpRequestService;

    @Autowired
    ProductDetialService productDetialService;

    @Autowired
    ProductAnalysisService productAnalysisService;


    public void sync() {
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


                        for (Map.Entry<String, Integer> entry : incomes.entrySet()) {
                            String channel = entry.getKey();
                            Integer income = entry.getValue();

                            ProductAnalysis analysis = new ProductAnalysis();

                            analysis.setProductId(detial.getProductId());
                            analysis.setProdIdentification(channel);
                            analysis.setOrderAmounts(income);
                            analysis.setSyncDate(DateUtils.specifyDate(1));
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
                            sdkBasicAnalysis.setSyncDate(DateUtils.specifyDate(1));

                            productAnalysisService.updateSdkBasicAnalysis(sdkBasicAnalysis);

                            Log.productAnalysisJob.info("LeTuSyncJob Run at:{} sucess update analysis:{}", new Date().toLocaleString(), analysis);
                        }
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
