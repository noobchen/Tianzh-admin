package com.tianzh.admin.business.analysis.model;

import java.util.Date;

/**
 * Created by pig on 2015-10-16.
 */
public class SdkProvinceAnalysis {

    int id;
    Date createTime;
    String productKey;
    String productName;
    String prodIdentification;
    int userId;
    String userCompany;
    int providerId;
    String providerName;
    int payId;
    String payName;
    int  orderAmounts;
    int sucessAmounts;
    int provinceId;
    String provinceName;
    String syncDate;

    public String getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProdIdentification() {
        return prodIdentification;
    }

    public void setProdIdentification(String prodIdentification) {
        this.prodIdentification = prodIdentification;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public int getOrderAmounts() {
        return orderAmounts;
    }

    public void setOrderAmounts(int orderAmounts) {
        this.orderAmounts = orderAmounts;
    }

    public int getSucessAmounts() {
        return sucessAmounts;
    }

    public void setSucessAmounts(int sucessAmounts) {
        this.sucessAmounts = sucessAmounts;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "SdkProvinceAnalysis{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", productKey='" + productKey + '\'' +
                ", productName='" + productName + '\'' +
                ", prodIdentification='" + prodIdentification + '\'' +
                ", userId=" + userId +
                ", userCompany='" + userCompany + '\'' +
                ", providerId=" + providerId +
                ", providerName='" + providerName + '\'' +
                ", payId=" + payId +
                ", payName='" + payName + '\'' +
                ", orderAmounts=" + orderAmounts +
                ", sucessAmounts=" + sucessAmounts +
                ", provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                ", syncDate='" + syncDate + '\'' +
                '}';
    }
}
