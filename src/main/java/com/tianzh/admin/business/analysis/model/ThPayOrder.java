package com.tianzh.admin.business.analysis.model;

import java.util.Date;

/**
 * Created by pig on 2015-09-12.
 */
public class ThPayOrder {
    int id;
    String userToken;
    String tianzhOrderId;
    String userOrderId;
    String appId;
    String appName;
    String channelId;
    String productId;
    String productName;
    int productPrice;
    String imsi;
    String imei;
    String phone;
    String province;
    String city;
    int providerId;
    String model;
    int thpayType;
    String thpayName;
    int orderStatus;    //0：计费成功 1：计费失败 2：发送成功 3：发送失败 4：支付中 5：支付异常
    String statusDesc;
    Date createTime;
    Date updateTime;
    Date tokenRegTime;

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public Date getTokenRegTime() {
        return tokenRegTime;
    }

    public void setTokenRegTime(Date tokenRegTime) {
        this.tokenRegTime = tokenRegTime;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTianzhOrderId() {
        return tianzhOrderId;
    }

    public void setTianzhOrderId(String tianzhOrderId) {
        this.tianzhOrderId = tianzhOrderId;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getThpayType() {
        return thpayType;
    }

    public void setThpayType(int thpayType) {
        this.thpayType = thpayType;
    }

    public String getThpayName() {
        return thpayName;
    }

    public void setThpayName(String thpayName) {
        this.thpayName = thpayName;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ThPayBean{");
        sb.append("id=").append(id);
        sb.append(", tianzhOrderId='").append(tianzhOrderId).append('\'');
        sb.append(", userOrderId='").append(userOrderId).append('\'');
        sb.append(", userToken='").append(userToken).append('\'');
        sb.append(", appId='").append(appId).append('\'');
        sb.append(", appName='").append(appName).append('\'');
        sb.append(", channelId='").append(channelId).append('\'');
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", productPrice=").append(productPrice);
        sb.append(", imsi='").append(imsi).append('\'');
        sb.append(", imei='").append(imei).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", thpayType=").append(thpayType);
        sb.append(", thpayName='").append(thpayName).append('\'');
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", statusDesc='").append(statusDesc).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
