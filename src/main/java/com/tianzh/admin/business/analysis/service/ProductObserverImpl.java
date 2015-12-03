package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.Product;
import com.tianzh.admin.business.analysis.model.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by pig on 2015-06-07.
 */
@Component("ProdObserver")
public class ProductObserverImpl implements CacheContainerObserver {

    @Override
    public void onAdd(Object object) {
        Product product = (Product) object;

        String productRefKey = Constant.ReferenceKey.PRODUCTKEY + product.getId();

        CacheContainer.setProductName(productRefKey, product.getName());
        CacheContainer.setProductKey(productRefKey, product.getAppId());
        Log.cacheContainerObserver.info("ProdObserver.onAdd:{} set productName:{} setProductKey:{} to productRefKey:{}", new Object[]{product, product.getName(), product.getAppId(), productRefKey});

        CacheContainer.setProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + product.getAppId(), String.valueOf(product.getId()));
        Log.cacheContainerObserver.info("ProdObserver.onAdd:{} set setProductIdByTpk:{} to Tpk:{}", new Object[]{product, product.getId(), Constant.ReferenceKey.THIRDPARTKEY + product.getAppId()});
    }

    @Override
    public void onEdit(Object object) {
        Product product = (Product) object;

        String productRefKey = Constant.ReferenceKey.PRODUCTKEY + product.getId();

//        if (product.getStatus() == Constant.ProductStatus.CLOSED) {
//            CacheContainer.removeProduct(productRefKey);
//            return;
//        }

        CacheContainer.setProductName(productRefKey, product.getName());

        Log.cacheContainerObserver.info("ProdObserver.onEdit:{} set productName:{} to productRefKey:{}", new Object[]{product, product.getName(), productRefKey});

    }

    @Override
    public void onDel(Object object) {
        Product product = (Product) object;
//
//        String productRefKey = Constant.ReferenceKey.PRODUCTKEY + product.getId();
//
//        CacheContainer.removeProduct(productRefKey);
        Log.cacheContainerObserver.info("ProdObserver.onDel:{} ", product);

    }
}
