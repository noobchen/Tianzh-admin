package com.tianzh.admin.business.analysis.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pig on 2015-10-16.
 */
public class SdkBasicAnalysis {
    int id;
    Date createTime;
    String productKey;
    String productName;
    String prodIdentification;
    int userId;
    String userCompany;
    int newUsers;
    int orderUsers;
    int  orderAmounts;
    int sucessAmounts;
    ArrayList<SdkProvinceAnalysis> provinceAnalysis;
    String syncDate;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public int getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(int newUsers) {
        this.newUsers = newUsers;
    }

    public int getOrderUsers() {
        return orderUsers;
    }

    public void setOrderUsers(int orderUsers) {
        this.orderUsers = orderUsers;
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

    public ArrayList<SdkProvinceAnalysis> getProvinceAnalysis() {
        return provinceAnalysis;
    }

    public void setProvinceAnalysis(ArrayList<SdkProvinceAnalysis> provinceAnalysis) {
        this.provinceAnalysis = provinceAnalysis;
    }


    @Override
    public String toString() {
        return "SdkBasicAnalysis{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", productKey='" + productKey + '\'' +
                ", productName='" + productName + '\'' +
                ", prodIdentification='" + prodIdentification + '\'' +
                ", userId=" + userId +
                ", userCompany='" + userCompany + '\'' +
                ", newUsers=" + newUsers +
                ", orderUsers=" + orderUsers +
                ", orderAmounts=" + orderAmounts +
                ", sucessAmounts=" + sucessAmounts +
                ", provinceAnalysis=" + provinceAnalysis +
                ", syncDate='" + syncDate + '\'' +
                '}';
    }
}
