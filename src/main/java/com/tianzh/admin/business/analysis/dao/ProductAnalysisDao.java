package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.BasicProAnalysis;
import com.tianzh.admin.business.analysis.model.ProductAnalysis;
import com.tianzh.admin.business.analysis.model.SdkBasicAnalysis;
import com.tianzh.admin.business.analysis.model.SdkProvinceAnalysis;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-06.
 */
public interface ProductAnalysisDao {

    void addProductAnalysis(List<ProductAnalysis> productAnalysises);

    void editProductAnalysis(ProductAnalysis analysis);

    void delProductAnalysis(ProductAnalysis analysis);

    void updateProductAnalysisAmount(ProductAnalysis analysis);

    List<ProductAnalysis> queryProductAnalysis(HashMap<String,Object> params);

    List<BasicProAnalysis> queryProAnalyByProduct(HashMap<String,String> params);

    List<BasicProAnalysis> queryProAnalyByDay(HashMap<String,String> params);


    void updateSdkBasicAnalysis(SdkBasicAnalysis analysis);

    void updateSdkProvinceAnalysis(SdkProvinceAnalysis analysis);

    Integer findSdkProvinceAnalysisId(SdkProvinceAnalysis analysis);
}
