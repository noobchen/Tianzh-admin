package com.tianzh.admin.business.analysis.model;

import java.util.Date;

/**
 * Created by pig on 2015-09-14.
 */
public class FeePoint {
    int id;
    int productId;
    String productName;
    String feePointName;
    String feePointId;
    int price;
    String feePointDesc;
    int letuPayPointNum;
    String letuSdkChannelId;
    int letuPayType;
    int letuGameType;
    String letuShowUIKey;
    String letuMerchantKey;
    String letuPrice;
    String letuFeeDesc;
    int yLIsOnline;
    String yLGoodsId;
    String yLPrice;
    String yLFeeDesc;
    String zhangKey;
    String zhangPricePointId;
    String zhangAppVersion;
    String zhPrice;
    String zhFeeDesc;
    Date createTime;
    Date updateTime;

    public String getLetuPrice() {
        return letuPrice;
    }

    public void setLetuPrice(String letuPrice) {
        this.letuPrice = letuPrice;
    }

    public String getLetuFeeDesc() {
        return letuFeeDesc;
    }

    public void setLetuFeeDesc(String letuFeeDesc) {
        this.letuFeeDesc = letuFeeDesc;
    }

    public String getyLPrice() {
        return yLPrice;
    }

    public void setyLPrice(String yLPrice) {
        this.yLPrice = yLPrice;
    }

    public String getyLFeeDesc() {
        return yLFeeDesc;
    }

    public void setyLFeeDesc(String yLFeeDesc) {
        this.yLFeeDesc = yLFeeDesc;
    }

    public String getZhPrice() {
        return zhPrice;
    }

    public void setZhPrice(String zhPrice) {
        this.zhPrice = zhPrice;
    }

    public String getZhFeeDesc() {
        return zhFeeDesc;
    }

    public void setZhFeeDesc(String zhFeeDesc) {
        this.zhFeeDesc = zhFeeDesc;
    }

    public String getLetuMerchantKey() {
        return letuMerchantKey;
    }

    public void setLetuMerchantKey(String letuMerchantKey) {
        this.letuMerchantKey = letuMerchantKey;
    }

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

    public String getFeePointId() {
        return feePointId;
    }

    public void setFeePointId(String feePointId) {
        this.feePointId = feePointId;
    }

    public String getFeePointName() {
        return feePointName;
    }

    public void setFeePointName(String feePointName) {
        this.feePointName = feePointName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFeePointDesc() {
        return feePointDesc;
    }

    public void setFeePointDesc(String feePointDesc) {
        this.feePointDesc = feePointDesc;
    }

    public int getLetuPayPointNum() {
        return letuPayPointNum;
    }

    public void setLetuPayPointNum(int letuPayPointNum) {
        this.letuPayPointNum = letuPayPointNum;
    }

    public String getLetuSdkChannelId() {
        return letuSdkChannelId;
    }

    public void setLetuSdkChannelId(String letuSdkChannelId) {
        this.letuSdkChannelId = letuSdkChannelId;
    }

    public int getLetuPayType() {
        return letuPayType;
    }

    public void setLetuPayType(int letuPayType) {
        this.letuPayType = letuPayType;
    }

    public int getLetuGameType() {
        return letuGameType;
    }

    public void setLetuGameType(int letuGameType) {
        this.letuGameType = letuGameType;
    }

    public String getLetuShowUIKey() {
        return letuShowUIKey;
    }

    public void setLetuShowUIKey(String letuShowUIKey) {
        this.letuShowUIKey = letuShowUIKey;
    }

    public int getyLIsOnline() {
        return yLIsOnline;
    }

    public void setyLIsOnline(int yLIsOnline) {
        this.yLIsOnline = yLIsOnline;
    }

    public String getyLGoodsId() {
        return yLGoodsId;
    }

    public void setyLGoodsId(String yLGoodsId) {
        this.yLGoodsId = yLGoodsId;
    }

    public String getZhangPricePointId() {
        return zhangPricePointId;
    }

    public void setZhangPricePointId(String zhangPricePointId) {
        this.zhangPricePointId = zhangPricePointId;
    }

    public String getZhangKey() {
        return zhangKey;
    }

    public void setZhangKey(String zhangKey) {
        this.zhangKey = zhangKey;
    }

    public String getZhangAppVersion() {
        return zhangAppVersion;
    }

    public void setZhangAppVersion(String zhangAppVersion) {
        this.zhangAppVersion = zhangAppVersion;
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
        final StringBuilder sb = new StringBuilder("FeePoint{");
        sb.append("id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", feePointName='").append(feePointName).append('\'');
        sb.append(", feePointId='").append(feePointId).append('\'');
        sb.append(", price=").append(price);
        sb.append(", feePointDesc='").append(feePointDesc).append('\'');
        sb.append(", letuPayPointNum=").append(letuPayPointNum);
        sb.append(", letuSdkChannelId='").append(letuSdkChannelId).append('\'');
        sb.append(", letuPayType=").append(letuPayType);
        sb.append(", letuGameType=").append(letuGameType);
        sb.append(", letuShowUIKey='").append(letuShowUIKey).append('\'');
        sb.append(", letuMerchantKey='").append(letuMerchantKey).append('\'');
        sb.append(", letuPrice='").append(letuPrice).append('\'');
        sb.append(", letuFeeDesc='").append(letuFeeDesc).append('\'');
        sb.append(", yLIsOnline=").append(yLIsOnline);
        sb.append(", yLGoodsId='").append(yLGoodsId).append('\'');
        sb.append(", yLPrice='").append(yLPrice).append('\'');
        sb.append(", yLFeeDesc='").append(yLFeeDesc).append('\'');
        sb.append(", zhangKey='").append(zhangKey).append('\'');
        sb.append(", zhangPricePointId='").append(zhangPricePointId).append('\'');
        sb.append(", zhangAppVersion='").append(zhangAppVersion).append('\'');
        sb.append(", zhPrice='").append(zhPrice).append('\'');
        sb.append(", zhFeeDesc='").append(zhFeeDesc).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
