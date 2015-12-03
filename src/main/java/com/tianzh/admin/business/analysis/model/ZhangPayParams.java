package com.tianzh.admin.business.analysis.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by pig on 2015-09-11.
 */
public class ZhangPayParams {
//    channelId		channelId，在我司后台申请，非空
//    key				用户私钥，非空
//    priciePointId	计费priciePointId，在我司后台申请，非空
//    PriciepointName	计费点对应的名称，非空
//    PriciepointDec	计费点的备注，非空
//    money			计费金额以分为单位，非空
//    Cpparam			cp设置的订单透传参数不能超过128位
//    appId			cp应用id 非空
//    appName			应用的名称，非空
//    appVersion		应用的版本号，非空
//    qd				用户自定义渠道，和申请后台的一样，非空

    String key;
    String priciePointId;
    String PriciepointName;
    String PriciepointDec;
    String money;
    String Cpparam;
    String appName;
    String appVersion;

    @JsonIgnore
    String tianzhOrderId;
    @JsonIgnore
    String statusCode;
    @JsonIgnore
    String thpayType = "3";
    @JsonIgnore
    String productId;
    @JsonIgnore
    String userOrderId;

    public String getTianzhOrderId() {
        return tianzhOrderId;
    }

    public void setTianzhOrderId(String tianzhOrderId) {
        this.tianzhOrderId = tianzhOrderId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getThpayType() {
        return thpayType;
    }

    public void setThpayType(String thpayType) {
        this.thpayType = thpayType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPriciePointId() {
        return priciePointId;
    }

    public void setPriciePointId(String priciePointId) {
        this.priciePointId = priciePointId;
    }

    public String getPriciepointName() {
        return PriciepointName;
    }

    public void setPriciepointName(String priciepointName) {
        PriciepointName = priciepointName;
    }

    public String getPriciepointDec() {
        return PriciepointDec;
    }

    public void setPriciepointDec(String priciepointDec) {
        PriciepointDec = priciepointDec;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCpparam() {
        return Cpparam;
    }

    public void setCpparam(String cpparam) {
        Cpparam = cpparam;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZhangPayParams{");
        sb.append("key='").append(key).append('\'');
        sb.append(", priciePointId='").append(priciePointId).append('\'');
        sb.append(", PriciepointName='").append(PriciepointName).append('\'');
        sb.append(", PriciepointDec='").append(PriciepointDec).append('\'');
        sb.append(", money='").append(money).append('\'');
        sb.append(", Cpparam='").append(Cpparam).append('\'');
        sb.append(", appName='").append(appName).append('\'');
        sb.append(", appVersion='").append(appVersion).append('\'');
        sb.append(", tianzhOrderId='").append(tianzhOrderId).append('\'');
        sb.append(", statusCode='").append(statusCode).append('\'');
        sb.append(", thpayType='").append(thpayType).append('\'');
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", userOrderId='").append(userOrderId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
