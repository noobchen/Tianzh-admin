package com.tianzh.admin.business.analysis.model;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by pig on 2015-09-11.
 */
public class YLPayParams {
//    is_online		是否为联网游戏
//    unit_price		商品单价(单位:分)
//    quantity 		商品数量
//    goods_id		商品编号（由支付平台生成，需要鉴权并统计商品销售情况）
//    goods_name		商品名称
//    user_order_id	用户订单号（由商户的应用在每次发起计费的时候根据自己的算法生成的一个订单号，订单号请勿重复使用）
//    requestType 	onActivityResult回调

    String is_online;
    String unit_price;
    String quantity = "1";
    String goods_id;
    String goods_name;
    @JsonIgnore
    String user_order_id;

    String requestType = "200";
    String appName;
    @JsonIgnore
    String tianzhOrderId;
    @JsonIgnore
    String statusCode;
    @JsonIgnore
    String thpayType = "2";
    @JsonIgnore
    String productId;
    @JsonIgnore
    String userOrderId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTianzhOrderId() {
        return tianzhOrderId;
    }

    public void setTianzhOrderId(String tianzhOrderId) {
        this.tianzhOrderId = tianzhOrderId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getThpayType() {
        return thpayType;
    }

    public void setThpayType(String thpayType) {
        this.thpayType = thpayType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getUser_order_id() {
        return user_order_id;
    }

    public void setUser_order_id(String user_order_id) {
        this.user_order_id = user_order_id;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("YLPayParams{");
        sb.append("is_online='").append(is_online).append('\'');
        sb.append(", unit_price='").append(unit_price).append('\'');
        sb.append(", quantity='").append(quantity).append('\'');
        sb.append(", goods_id='").append(goods_id).append('\'');
        sb.append(", goods_name='").append(goods_name).append('\'');
        sb.append(", user_order_id='").append(user_order_id).append('\'');
        sb.append(", requestType='").append(requestType).append('\'');
        sb.append(", tianzhOrderId='").append(tianzhOrderId).append('\'');
        sb.append(", statusCode='").append(statusCode).append('\'');
        sb.append(", thpayType='").append(thpayType).append('\'');
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", userOrderId='").append(userOrderId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
