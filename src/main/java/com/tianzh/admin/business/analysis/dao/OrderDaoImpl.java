package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
@Component()
public class OrderDaoImpl extends SqlSessionTemplate implements OrderDao {

    @Autowired
    public OrderDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public ThPayOrder findThOrder(Integer id) {
        return (ThPayOrder) selectOne("order.findThOrder", id);
    }

    @Override
    public void addOrder(Order order) {
        insert("order.addOrder", order);
    }

    @Override
    public List<Order> queryOrders(HashMap<String, Object> params) {
        return (List<Order>) selectList("order.queryOrders", params);
    }

    @Override
    public List<ProductAnalysis> analysisOrders(HashMap<String, Object> params) {
        return (List<ProductAnalysis>) selectList("order.analysisOrders", params);
    }

    @Override
    public ProductAnalysis analysisCpOrders(HashMap<String, Object> params) {

        return (ProductAnalysis) selectOne("order.analysisCpOrders", params);
    }

    @Override
    public int queryNewAmounts(HashMap<String, Object> params) {
        Integer orderAmounts = (Integer) selectOne("order.queryNewAmounts", params);

        return orderAmounts == null ? 0 : orderAmounts;
    }

    @Override
    public void updateOrder(Order order) {
        insert("order.updateOrder", order);
    }

    @Override
    public void updateCpOrder(Order order) {
        insert("order.updateCpOrder", order);
    }

    @Override
    public void updateChargeOrder(HashMap<String, Object> params) {
        update("order.updateChargeOrder", params);
    }

    @Override
    public List<ThPayOrder> queryThOrders(HashMap<String, Object> params) {
        return (List<ThPayOrder>) selectList("order.queryThOrders", params);
    }

    @Override
    public List<BasicProAnalysis> queryProAnalyByProduct(HashMap<String, String> params) {
        return (List<BasicProAnalysis>) selectList("order.queryProAnalyByProduct", params);
    }

    @Override
    public List<SdkBasicAnalysis> analysisBasicChargeOrders(HashMap<String, String> params) {
        return (List<SdkBasicAnalysis>) selectList("order.analysisBasicChargeOrders", params);
    }

    @Override
    public List<SdkProvinceAnalysis> analysisBasicSdk(HashMap<String, String> params) {
        return (List<SdkProvinceAnalysis>) selectList("order.analysisBasicSdk", params);
    }

    @Override
    public List<SdkBasicAnalysis> queryBasicChargeAnalysises(HashMap<String, String> params) {
        return (List<SdkBasicAnalysis>) selectList("order.queryBasicChargeAnalysises", params);
    }

    @Override
    public List<SdkProvinceAnalysis> queryBasicSdkAnalysises(HashMap<String, String> params) {
        return (List<SdkProvinceAnalysis>) selectList("order.queryBasicSdkAnalysises", params);
    }

    @Override
    public List<SdkProvinceAnalysis> analysisDetialSdk(HashMap<String, String> params) {
        return (List<SdkProvinceAnalysis>) selectList("order.analysisDetialSdk", params);
    }

    @Override
    public Integer queryBasicSucessAmounts(HashMap<String, String> params) {
        return (Integer) selectOne("order.queryBasicSucessAmounts", params);
    }

    @Override
    public void addSdkBasicAnalysises(ArrayList<SdkBasicAnalysis> sdkBasicAnalysises) {
        insert("order.addSdkBasicAnalysises", sdkBasicAnalysises);
    }

    @Override
    public void addSdkProvinceAnalysises(ArrayList<SdkProvinceAnalysis> SdkProvinceAnalysis) {
        insert("order.addSdkProvinceAnalysises", SdkProvinceAnalysis);
    }

    @Override
    public ArrayList<SdkProvinceAnalysis> analysisBasicProvinceAnalysis(HashMap<String, Object> params) {
        return (ArrayList<SdkProvinceAnalysis>) selectList("order.analysisBasicProvinceAnalysis", params);
    }

    @Override
    public ArrayList<SdkProvinceAnalysis> queryBasicProvinceAnalysis(HashMap<String, Object> params) {
        return (ArrayList<SdkProvinceAnalysis>) selectList("order.queryBasicProvinceAnalysis", params);
    }
}
