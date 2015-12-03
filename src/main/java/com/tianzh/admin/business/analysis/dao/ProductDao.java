package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.Product;
import com.tianzh.admin.common.page.PageInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
public interface ProductDao {

    void addProduct(Product product);

    List<Product> queryProduct(HashMap<String,Object> params);

    List<Product> queryProduct4Cooperation(HashMap<String,Object> params);

    void editProduct(Product product);

    void deleteProduct(Product product);

}
