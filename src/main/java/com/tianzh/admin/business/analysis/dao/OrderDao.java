package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
public interface OrderDao {

    ThPayOrder findThOrder(Integer id);

    //增加订单信息
    void addOrder(Order order);
    //查询订单
    List<Order> queryOrders(HashMap<String,Object> params);
    //数据分析
    List<ProductAnalysis> analysisOrders(HashMap<String,Object> params);

    ProductAnalysis analysisCpOrders(HashMap<String, Object> params);

    //统计付费成功金额
    int queryNewAmounts(HashMap<String, Object> params);

    void updateOrder(Order order);

    void updateCpOrder(Order order);

    void updateChargeOrder(HashMap<String,Object> params);

    List<ThPayOrder> queryThOrders(HashMap<String, Object> params);

    List<BasicProAnalysis> queryProAnalyByProduct(HashMap<String,String> params);

    //分析SDK数据
    List<SdkBasicAnalysis> analysisBasicChargeOrders(HashMap<String,String> params);

    List<SdkProvinceAnalysis> analysisBasicSdk(HashMap<String,String> params);

    //查询SDK数据
    List<SdkBasicAnalysis> queryBasicChargeAnalysises(HashMap<String,String> params);

    List<SdkProvinceAnalysis> queryBasicSdkAnalysises(HashMap<String,String> params);

    List<SdkProvinceAnalysis> analysisDetialSdk(HashMap<String,String> params);

    Integer queryBasicSucessAmounts(HashMap<String,String> params);

    void addSdkBasicAnalysises(ArrayList<SdkBasicAnalysis> SdkBasicAnalysises);

    void addSdkProvinceAnalysises(ArrayList<SdkProvinceAnalysis> SdkProvinceAnalysis);

    ArrayList<SdkProvinceAnalysis> analysisBasicProvinceAnalysis(HashMap<String,Object> params);

    ArrayList<SdkProvinceAnalysis> queryBasicProvinceAnalysis(HashMap<String,Object> params);
}
