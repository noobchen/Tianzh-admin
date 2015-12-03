package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.BasicProAnalysis;
import com.tianzh.admin.business.analysis.model.ProductAnalysis;
import com.tianzh.admin.business.analysis.model.SdkBasicAnalysis;
import com.tianzh.admin.business.analysis.model.SdkProvinceAnalysis;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-06.
 */
@Component
public class ProductAnalysisDaoImpl extends SqlSessionTemplate implements ProductAnalysisDao {

    @Autowired
    public ProductAnalysisDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public void addProductAnalysis(List<ProductAnalysis> productAnalysises) {
        insert("product_analysis.addProductAnalysis2", productAnalysises);
    }

    @Override
    public void editProductAnalysis(ProductAnalysis analysis) {
        update("product_analysis.editProductAnalysis", analysis);
    }

    @Override
    public void delProductAnalysis(ProductAnalysis analysis) {
        delete("product_analysis.delProductAnalysis", analysis);
    }

    @Override
    public void updateProductAnalysisAmount(ProductAnalysis analysis) {
        update("product_analysis.updateProductAnalysisAmount", analysis);
    }

    @Override
    public List<ProductAnalysis> queryProductAnalysis(HashMap<String, Object> params) {
        return (List<ProductAnalysis>) selectList("product_analysis.queryProductAnalysis", params);
    }

    @Override
    public List<BasicProAnalysis> queryProAnalyByProduct(HashMap<String,String> params) {
        return (List<BasicProAnalysis>) selectList("product_analysis.queryProAnalyByProduct",params);
    }

    @Override
    public List<BasicProAnalysis> queryProAnalyByDay(HashMap<String, String> params) {
        return (List<BasicProAnalysis>) selectList("product_analysis.queryProAnalyByDay",params);
    }

    @Override
    public void updateSdkBasicAnalysis(SdkBasicAnalysis analysis) {
        update("product_analysis.updateSdkBasicAnalysis", analysis);
    }

    @Override
    public void updateSdkProvinceAnalysis(SdkProvinceAnalysis analysis) {
        update("product_analysis.updateSdkProvinceAnalysis", analysis);
    }

    @Override
    public Integer findSdkProvinceAnalysisId(SdkProvinceAnalysis analysis) {
        return (Integer) selectOne("product_analysis.findSdkProvinceAnalysisId", analysis);
    }
}
