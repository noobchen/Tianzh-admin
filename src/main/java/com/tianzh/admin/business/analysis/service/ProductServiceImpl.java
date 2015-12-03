package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.dao.ProductDao;
import com.tianzh.admin.business.analysis.model.Product;
import com.tianzh.admin.common.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;

    @Override
    public void addProduct(Product product) throws Exception {
        try {
            productDao.addProduct(product);

            for (CacheContainerObserver observer : observers) {
                observer.onAdd(product);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PageInfo queryProduct(PageInfo pageInfo, HashMap<String,Object> params) {
        params.put("pageInfo", pageInfo);
        List<Product> products = productDao.queryProduct(params);

        pageInfo.setResult(products);
        return pageInfo;
    }

    @Override
    public List<Product> queryProduct(HashMap<String,Object> params) {
        return productDao.queryProduct(params);
    }

    @Override
    public List<Product> queryProduct4Cooperation(HashMap<String, Object> params) {
        return productDao.queryProduct4Cooperation(params);
    }

    @Override
    public void editProduct(Product product) throws Exception {
        try {
            productDao.editProduct(product);

            for (CacheContainerObserver observer : observers) {
                observer.onEdit(product);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteProduct(Product product) throws Exception {
        try {
            productDao.deleteProduct(product);

            for (CacheContainerObserver observer : observers) {
                observer.onDel(product);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void registerObserver(CacheContainerObserver observer) {
        observers.add(observer);
    }
}
