package com.tianzh.admin.business.analysis.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Created by pig on 2015-06-05.
 */
public class ProductDetial {
    int id;
    int productId;
    String productName;
    String thirdPartyKey;
    String thirdPartyAccount;
    String thirdPartyPwd;
    int keyType;
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

    public String getThirdPartyKey() {
        return thirdPartyKey;
    }

    public void setThirdPartyKey(String thirdPartyKey) {
        this.thirdPartyKey = thirdPartyKey;
    }

    public String getThirdPartyAccount() {
        return thirdPartyAccount;
    }

    public void setThirdPartyAccount(String thirdPartyAccount) {
        this.thirdPartyAccount = thirdPartyAccount;
    }

    public String getThirdPartyPwd() {
        return thirdPartyPwd;
    }

    public void setThirdPartyPwd(String thirdPartyPwd) {
        this.thirdPartyPwd = thirdPartyPwd;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
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
                .append("productId", productId)
                .append("productName", productName)
                .append("thirdPartyKey", thirdPartyKey)
                .append("thirdPartyAccount", thirdPartyAccount)
                .append("thirdPartyPwd", thirdPartyPwd)
                .append("keyType", keyType)
                .append("status", status)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .toString();
    }
}
