package com.tianzh.admin.business.analysis.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by pig on 2015-09-11.
 */
public class LTPayParams {
    @JsonIgnore
    String orderId;
    String payPointNum;
    String price;
    String productName;
    String orderDesc;
    String sdkChannelId;
    String payType;
    String gameType;
    String showUIKey;
    @JsonIgnore
    String ex;
    String appName;

    @JsonIgnore
    String tianzhOrderId;
    @JsonIgnore
    String statusCode;
    @JsonIgnore
    String thpayType = "1";
    @JsonIgnore
    String productId;
    @JsonIgnore
    String userOrderId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayPointNum() {
        return payPointNum;
    }

    public void setPayPointNum(String payPointNum) {
        this.payPointNum = payPointNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getSdkChannelId() {
        return sdkChannelId;
    }

    public void setSdkChannelId(String sdkChannelId) {
        this.sdkChannelId = sdkChannelId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getShowUIKey() {
        return showUIKey;
    }

    public void setShowUIKey(String showUIKey) {
        this.showUIKey = showUIKey;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LTPayParams{");
        sb.append("orderId='").append(orderId).append('\'');
        sb.append(", payPointNum='").append(payPointNum).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", orderDesc='").append(orderDesc).append('\'');
        sb.append(", sdkChannelId='").append(sdkChannelId).append('\'');
        sb.append(", payType='").append(payType).append('\'');
        sb.append(", gameType='").append(gameType).append('\'');
        sb.append(", showUIKey='").append(showUIKey).append('\'');
        sb.append(", ex='").append(ex).append('\'');
        sb.append(", appName='").append(appName).append('\'');
        sb.append(", tianzhOrderId='").append(tianzhOrderId).append('\'');
        sb.append(", statusCode='").append(statusCode).append('\'');
        sb.append(", thpayType='").append(thpayType).append('\'');
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", userOrderId='").append(userOrderId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
