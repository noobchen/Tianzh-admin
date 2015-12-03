package com.tianzh.admin.business.analysis.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Created by pig on 2015-06-05.
 */
public class ProductIdentification {

    int id;
    int productId;
    String productName;
    int userId;
    String userCompanyName;
    String accountType;
    String prodIdentification;
    int cooperationType;
    int chargeOffType;
    float sharing;
    float discount;
    int status;
    Date createTime;
    Date updateTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getProdIdentification() {
        return prodIdentification;
    }

    public void setProdIdentification(String prodIdentification) {
        this.prodIdentification = prodIdentification;
    }

    public int getCooperationType() {
        return cooperationType;
    }

    public void setCooperationType(int cooperationType) {
        this.cooperationType = cooperationType;
    }

    public int getChargeOffType() {
        return chargeOffType;
    }

    public void setChargeOffType(int chargeOffType) {
        this.chargeOffType = chargeOffType;
    }

    public float getSharing() {
        return sharing;
    }

    public void setSharing(float sharing) {
        this.sharing = sharing;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        ProductAnalysis that = (ProductAnalysis) o;

        if (productId != that.productId) return false;
        if (userId != that.userId) return false;
        return prodIdentification.equals(that.prodIdentification);

    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + userId;
        result = 31 * result + prodIdentification.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("productId", productId)
                .append("productName", productName)
                .append("userId", userId)
                .append("prodIdentification", prodIdentification)
                .append("cooperationType", cooperationType)
                .append("chargeOffType", chargeOffType)
                .append("discount", discount)
                .append("sharing", sharing)
                .append("status", status)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }


}
