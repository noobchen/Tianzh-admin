package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.model.BasicProAnalysis;
import com.tianzh.admin.business.analysis.model.ProductAnalysis;
import com.tianzh.admin.business.analysis.model.SdkBasicAnalysis;
import com.tianzh.admin.business.analysis.model.SdkProvinceAnalysis;
import com.tianzh.admin.common.page.PageInfo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-06.
 */
public interface ProductAnalysisService {

    void addProductAnalysis(List<ProductAnalysis> productAnalysises) throws Exception;

    void editProductAnalysis(ProductAnalysis analysis) throws Exception;

    void delProductAnalysis(ProductAnalysis analysis) throws Exception;

    void updateProductAnalysisAmount(ProductAnalysis analysis) throws Exception;

    void updateSdkBasicAnalysis(SdkBasicAnalysis analysis) throws Exception;

    void updateSdkProvinceAnalysis(SdkProvinceAnalysis analysis) throws Exception;

    PageInfo queryProductAnalysis(PageInfo pageInfo,HashMap<String,Object> params) throws Exception;

    List<ProductAnalysis> queryProductAnalysis(ProductAnalysis analysis, String startDate, String endDate) throws Exception;

    List<BasicProAnalysis> queryBasicProAnalysis() throws Exception;

    List<BasicProAnalysis> queryProAnalyByDay(HashMap<String,String> params) throws Exception;
}
