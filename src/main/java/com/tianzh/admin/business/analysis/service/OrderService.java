package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.model.*;
import com.tianzh.admin.common.page.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
public interface OrderService {

    ThPayOrder findThOrder(Integer id);

    //增加订单信息
    void addOrder(Order order) throws Exception;

    //查询订单
    List<Order> queryOrders(Order order, String startDate, String endDate);

    //数据分析
    List<ProductAnalysis> analysisOrders(Order order, String specifiedDate) throws Exception;

    //统计付费成功金额
    int queryNewAmounts(ProductAnalysis productAnalysis, String specifiedDate);

    void updateOrder(Order order) throws Exception;

    void updateCpOrder(Order order) throws Exception;

    void updateChargeOrder(HashMap<String,Object> params) throws Exception;

    PageInfo queryThOrders(PageInfo pageInfo, HashMap<String, Object> params);

    List<BasicProAnalysis> queryProAnalyByProduct(HashMap<String,String> params);

    //查询SDK数据
    List<SdkBasicAnalysis> queryBasicChargeAnalysises(HashMap<String,String> params);


    List<SdkBasicAnalysis> analysisBasicChargeOrders(HashMap<String,String> params);


    //分析SDK省份数据
    List<SdkProvinceAnalysis> analysisDetialSdk(HashMap<String,String> params);

    //分析SDK基础数据
    List<SdkBasicAnalysis> analysisBasicSdk(HashMap<String,String> params);

    void addSdkBasicAnalysises(ArrayList<SdkBasicAnalysis> sdkBasicAnalysises) throws Exception;

    void addSdkProvinceAnalysises(ArrayList<SdkProvinceAnalysis> sdkProvinceAnalysises) throws Exception;

    ArrayList<SdkProvinceAnalysis> queryBasicProvinceAnalysis(HashMap<String, Object> params);
}
