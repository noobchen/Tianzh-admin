package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.dao.ProductIdentificationDao;
import com.tianzh.admin.business.analysis.model.ProductIdentification;
import com.tianzh.admin.common.page.PageInfo;
import com.tianzh.admin.common.page.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-07.
 */
@Component
public class ProductIdentificationServiceImpl implements ProductIdentificationService {

    @Autowired
    ProductIdentificationDao productIdentificationDao;

    @Override
    public void addProductIdentification(ProductIdentification productIdentification) throws Exception {
        try {
            productIdentificationDao.addProductIdentification(productIdentification);

            for (CacheContainerObserver observer : observers) {
                observer.onAdd(productIdentification);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void editProductIdentification(ProductIdentification productIdentification) throws Exception {
        try {
            productIdentificationDao.editProductIdentification(productIdentification);

            for (CacheContainerObserver observer : observers) {
                observer.onEdit(productIdentification);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delProductIdentification(ProductIdentification productIdentification) throws Exception {
        try {
            productIdentificationDao.delProductIdentification(productIdentification);

            for (CacheContainerObserver observer : observers) {
                observer.onDel(productIdentification);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PageInfo queryProductIdentification(PageInfo pageInfo, HashMap<String,Object> params) {
        params.put("pageInfo", pageInfo);

        List<ProductIdentification> productIdentifications = productIdentificationDao.queryProductIdentification(params);

        pageInfo.setResult(productIdentifications);

        return pageInfo;
    }

    @Override
    public List<ProductIdentification> queryProductIdentification(ProductIdentification productIdentification) {
        HashMap<String, Object> params = new HashMap<String, Object>();

        if (productIdentification != null) {
            params.put("productId", productIdentification.getProductId());
            params.put("prodIdentification", productIdentification.getProdIdentification());
            params.put("userId", productIdentification.getUserId());
        }

        return productIdentificationDao.queryProductIdentification(params);
    }

    @Override
    public List<ProductIdentification> queryProIdentiGroup(ProductIdentification productIdentification) {
        HashMap<String, Object> params = new HashMap<String, Object>();

        if (productIdentification != null) {
            params.put("productId", productIdentification.getProductId());
            params.put("prodIdentification", productIdentification.getProdIdentification());
            params.put("userId", productIdentification.getUserId());
        }

        return productIdentificationDao.queryProIdentiGroup(params);
    }

    @Override
    public List<ProductIdentification> queryAllProIdentiGroup() {
        return productIdentificationDao.queryAllProIdentiGroup();
    }

    @Override
    public void registerObserver(CacheContainerObserver observer) {
        observers.add(observer);
    }
}
