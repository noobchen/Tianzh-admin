package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.model.ProductIdentification;
import com.tianzh.admin.common.page.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-07.
 */
public interface ProductIdentificationService {

    List<CacheContainerObserver> observers = new ArrayList<CacheContainerObserver>();

    void addProductIdentification(ProductIdentification productIdentification) throws Exception;

    void editProductIdentification(ProductIdentification productIdentification) throws Exception;

    void delProductIdentification(ProductIdentification productIdentification) throws Exception;

    PageInfo queryProductIdentification(PageInfo pageInfo,HashMap<String,Object> params);

    List<ProductIdentification> queryProductIdentification(ProductIdentification productIdentification);

    List<ProductIdentification> queryProIdentiGroup(ProductIdentification productIdentification);

    List<ProductIdentification> queryAllProIdentiGroup();

    void registerObserver(CacheContainerObserver observer);
}
