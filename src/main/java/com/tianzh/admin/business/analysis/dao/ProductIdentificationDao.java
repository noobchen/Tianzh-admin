package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.ProductIdentification;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-07.
 */
public interface ProductIdentificationDao {
    void addProductIdentification(ProductIdentification productIdentification);

    void editProductIdentification(ProductIdentification productIdentification);

    void delProductIdentification(ProductIdentification productIdentification);

    List<ProductIdentification> queryProductIdentification(HashMap<String,Object> params);

    List<ProductIdentification> queryProIdentiGroup(HashMap<String, Object> params);

    List<ProductIdentification> queryAllProIdentiGroup();
}
