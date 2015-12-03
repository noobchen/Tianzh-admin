package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.model.FeePoint;
import com.tianzh.admin.business.analysis.model.ProductDetial;
import com.tianzh.admin.common.page.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
public interface ProductDetialService {
    List<CacheContainerObserver> observers = new ArrayList<CacheContainerObserver>();


    void addProductDetial(ProductDetial detial) throws Exception;

    void delProductDetial(ProductDetial detial) throws Exception;

    void editProductDetial(ProductDetial detial) throws Exception;

    List<ProductDetial> queryProductDetials(HashMap<String, Object> params);

    PageInfo queryProductDetials(PageInfo pageInfo, HashMap<String, Object> params);

    void registerObserver(CacheContainerObserver observer);

    void addFeePointInfo(FeePoint feePoint) throws Exception;

    void addFeePointLetu(FeePoint feePoint) throws Exception;

    void addFeePointYL(FeePoint feePoint) throws Exception;

    void addFeePointZhang(FeePoint feePoint) throws Exception;

    List<FeePoint> queryFeePointInfo(HashMap<String, Object> params);

    FeePoint queryFeePointLetu(HashMap<String, Object> params);

    FeePoint queryFeePointYL(HashMap<String, Object> params);

    FeePoint queryFeePointZhang(HashMap<String, Object> params);


    void editFeePointInfo(FeePoint feePoint) throws Exception;

    void editFeePointLetu(FeePoint feePoint) throws Exception;

    void editFeePointYL(FeePoint feePoint) throws Exception;

    void editFeePointZhang(FeePoint feePoint) throws Exception;


    void delFeePointInfo(FeePoint feePoint) throws Exception;

    void delFeePointLetu(FeePoint feePoint) throws Exception;

    void delFeePointYL(FeePoint feePoint) throws Exception;

    void delFeePointZhang(FeePoint feePoint) throws Exception;
}
