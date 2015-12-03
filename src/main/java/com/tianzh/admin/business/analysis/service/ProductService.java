package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.model.Product;
import com.tianzh.admin.common.page.PageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
public interface ProductService {

    List<CacheContainerObserver> observers = new ArrayList<CacheContainerObserver>();

    void addProduct(Product product) throws Exception;

    PageInfo queryProduct(PageInfo pageInfo, HashMap<String,Object> params);

    List<Product> queryProduct(HashMap<String,Object> params);

    List<Product> queryProduct4Cooperation(HashMap<String,Object> params);

    void editProduct(Product product) throws Exception;

    void deleteProduct(Product product) throws Exception;

    void registerObserver(CacheContainerObserver observer);
}
