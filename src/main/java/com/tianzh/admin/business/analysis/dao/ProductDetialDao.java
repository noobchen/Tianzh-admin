package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.FeePoint;
import com.tianzh.admin.business.analysis.model.ProductDetial;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
public interface ProductDetialDao {

    void addProductDetial(ProductDetial detial);

    void delProductDetial(ProductDetial detial);

    void editProductDetial(ProductDetial detial);

    List<ProductDetial> queryProductDetials(HashMap<String, Object> params);

    void addFeePointInfo(FeePoint feePoint);

    void addFeePointLetu(FeePoint feePoint);

    void addFeePointYL(FeePoint feePoint);

    void addFeePointZhang(FeePoint feePoint);

    List<FeePoint> queryFeePointInfo(HashMap<String, Object> params);

    FeePoint queryFeePointLetu(HashMap<String, Object> params);

    FeePoint queryFeePointYL(HashMap<String, Object> params);

    FeePoint queryFeePointZhang(HashMap<String, Object> params);


    void editFeePointInfo(FeePoint feePoint);

    void editFeePointLetu(FeePoint feePoint);

    void editFeePointYL(FeePoint feePoint);

    void editFeePointZhang(FeePoint feePoint);


    void delFeePointInfo(FeePoint feePoint);

    void delFeePointLetu(FeePoint feePoint);

    void delFeePointYL(FeePoint feePoint);

    void delFeePointZhang(FeePoint feePoint);


}
