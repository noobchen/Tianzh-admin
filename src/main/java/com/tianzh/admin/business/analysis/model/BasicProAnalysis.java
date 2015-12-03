package com.tianzh.admin.business.analysis.model;

/**
 * Created by pig on 2015-10-08.
 */
public class BasicProAnalysis {
    private int appId;
    private String appName;
    private int newUsers;
    private int amounts;
    private float arup;
    private int totalNewUsers;
    private int totalAmounts;
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(int newUsers) {
        this.newUsers = newUsers;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public float getArup() {
        return arup;
    }

    public void setArup(float arup) {
        this.arup = arup;
    }

    public int getTotalNewUsers() {
        return totalNewUsers;
    }

    public void setTotalNewUsers(int totalNewUsers) {
        this.totalNewUsers = totalNewUsers;
    }

    public int getTotalAmounts() {
        return totalAmounts;
    }

    public void setTotalAmounts(int totalAmounts) {
        this.totalAmounts = totalAmounts;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BasicProAnalysis{");
        sb.append("appId=").append(appId);
        sb.append(", appName='").append(appName).append('\'');
        sb.append(", newUsers=").append(newUsers);
        sb.append(", amounts=").append(amounts);
        sb.append(", arup=").append(arup);
        sb.append(", totalNewUsers=").append(totalNewUsers);
        sb.append(", totalAmounts=").append(totalAmounts);
        sb.append('}');
        return sb.toString();
    }
}
