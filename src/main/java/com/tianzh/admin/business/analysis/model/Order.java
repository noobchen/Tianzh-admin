package com.tianzh.admin.business.analysis.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Created by pig on 2015-06-05.
 */
public class Order {

    int id;
    int chargeOrderId;
    String linkId;
    String payType;
    int productId;
    String thirdPartyKey;
    String productName;
    String prodIdentification;
    int userId;
    String userCompany;
    String userToken;
    int amounts;
    int status = 999;
    String syncDate;
    Date registerTime;
    Date createTime;
    Date updateTime;

    public int getChargeOrderId() {
        return chargeOrderId;
    }

    public void setChargeOrderId(int chargeOrderId) {
        this.chargeOrderId = chargeOrderId;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getThirdPartyKey() {
        return thirdPartyKey;
    }

    public void setThirdPartyKey(String thirdPartyKey) {
        this.thirdPartyKey = thirdPartyKey;
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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        return new ToStringBuilder(this)
                .append("id", id)
                .append("linkId", linkId)
                .append("payType", payType)
                .append("productId", productId)
                .append("thirdPartyKey", thirdPartyKey)
                .append("productName", productName)
                .append("prodIdentification", prodIdentification)
                .append("userId", userId)
                .append("userCompany", userCompany)
                .append("userToken", userToken)
                .append("amounts", amounts)
                .append("status", status)
                .append("registerTime", registerTime)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }
}
