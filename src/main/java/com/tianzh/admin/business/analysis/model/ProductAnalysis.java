package com.tianzh.admin.business.analysis.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Created by pig on 2015-06-05.
 */
public class ProductAnalysis {

    int id;
    Integer productId;
    String productName;
    String prodIdentification;
    int userId;
    String userCompany;
    int cooperationType;
    int chargeOffType;
    float sharing;
    int newUsers;
    int usefullUsers;
    int activeUsers;
    int orderAmounts;
    int newAmounts;
    float usersDiscount;
    float amountsDiscount;
    int chargeOffStatus = 0;
    int showNewUsers;
    int showOrderAmounts;
    Date createTime;
    Date updateTime;
    String syncDate;
    float userfullPercent;
    float arpu;
    float narpu;

    public float getUserfullPercent() {
        return userfullPercent;
    }

    public void setUserfullPercent(float userfullPercent) {
        this.userfullPercent = userfullPercent;
    }

    public float getArpu() {
        return arpu;
    }

    public void setArpu(float arpu) {
        this.arpu = arpu;
    }

    public float getNarpu() {
        return narpu;
    }

    public void setNarpu(float narpu) {
        this.narpu = narpu;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public int getCooperationType() {
        return cooperationType;
    }

    public void setCooperationType(int cooperationType) {
        this.cooperationType = cooperationType;
    }

    public int getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(int newUsers) {
        this.newUsers = newUsers;
    }


    public int getOrderAmounts() {
        return orderAmounts;
    }

    public void setOrderAmounts(int orderAmounts) {
        this.orderAmounts = orderAmounts;
    }


    public int getChargeOffStatus() {
        return chargeOffStatus;
    }

    public void setChargeOffStatus(int chargeOffStatus) {
        this.chargeOffStatus = chargeOffStatus;
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

    public int getShowNewUsers() {
        return showNewUsers;
    }

    public void setShowNewUsers(int showNewUsers) {
        this.showNewUsers = showNewUsers;
    }

    public int getShowOrderAmounts() {
        return showOrderAmounts;
    }

    public void setShowOrderAmounts(int showOrderAmounts) {
        this.showOrderAmounts = showOrderAmounts;
    }

    public int getUsefullUsers() {
        return usefullUsers;
    }

    public void setUsefullUsers(int usefullUsers) {
        this.usefullUsers = usefullUsers;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    public int getNewAmounts() {
        return newAmounts;
    }

    public void setNewAmounts(int newAmounts) {
        this.newAmounts = newAmounts;
    }

    public float getUsersDiscount() {
        return usersDiscount;
    }

    public void setUsersDiscount(float usersDiscount) {
        this.usersDiscount = usersDiscount;
    }

    public float getAmountsDiscount() {
        return amountsDiscount;
    }

    public void setAmountsDiscount(float amountsDiscount) {
        this.amountsDiscount = amountsDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        ProductAnalysis analysis = (ProductAnalysis) o;

        if (productId != analysis.productId) return false;
        if (userId != analysis.userId) return false;
        return prodIdentification.equals(analysis.prodIdentification);

    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + prodIdentification.hashCode();
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductAnalysis{");
        sb.append("id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", prodIdentification='").append(prodIdentification).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", userCompany='").append(userCompany).append('\'');
        sb.append(", cooperationType=").append(cooperationType);
        sb.append(", chargeOffType=").append(chargeOffType);
        sb.append(", sharing=").append(sharing);
        sb.append(", newUsers=").append(newUsers);
        sb.append(", usefullUsers=").append(usefullUsers);
        sb.append(", activeUsers=").append(activeUsers);
        sb.append(", orderAmounts=").append(orderAmounts);
        sb.append(", newAmounts=").append(newAmounts);
        sb.append(", usersDiscount=").append(usersDiscount);
        sb.append(", amountsDiscount=").append(amountsDiscount);
        sb.append(", chargeOffStatus=").append(chargeOffStatus);
        sb.append(", showNewUsers=").append(showNewUsers);
        sb.append(", showOrderAmounts=").append(showOrderAmounts);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", syncDate='").append(syncDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
