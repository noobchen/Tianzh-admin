package com.tianzh.admin.business.analysis.model;

import java.util.Date;

/**
 * Created by pig on 2015-09-15.
 */
public class ThPayProvince {
    int id;
    int thPayId;
    int provinderId;
    int provinceId;
    int provinceType;
    String province;
    Date createTime;
    Date updateTime;

    public int getProvinceType() {
        return provinceType;
    }

    public void setProvinceType(int provinceType) {
        this.provinceType = provinceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThPayId() {
        return thPayId;
    }

    public void setThPayId(int thPayId) {
        this.thPayId = thPayId;
    }

    public int getProvinderId() {
        return provinderId;
    }

    public void setProvinderId(int provinderId) {
        this.provinderId = provinderId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
        final StringBuilder sb = new StringBuilder("ThPayProvince{");
        sb.append("id=").append(id);
        sb.append(", thPayId=").append(thPayId);
        sb.append(", provinderId=").append(provinderId);
        sb.append(", provinceType=").append(provinceType);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", province='").append(province).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
