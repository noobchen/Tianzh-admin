package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.ProductDetial;
import com.tianzh.admin.business.analysis.model.CacheContainer;
import org.springframework.stereotype.Component;

/**
 * Created by pig on 2015-06-07.
 */
@Component("ProdDetialObserver")
public class ProductDetialObserverImpl implements CacheContainerObserver {


    @Override
    public void onAdd(Object object) {
        ProductDetial productDetial = (ProductDetial) object;

        String productRefKey = Constant.ReferenceKey.PRODUCTKEY + productDetial.getProductId();

        switch (productDetial.getKeyType()) {
            case Constant.KeyType.UMENGKEY:
                CacheContainer.setUmengKey(productRefKey, productDetial.getThirdPartyKey());
                String thirdPartyAccount = productDetial.getThirdPartyAccount();
                String thirdPartyPwd = productDetial.getThirdPartyPwd();

                String accountPwd = thirdPartyAccount + "|" + thirdPartyPwd;
                CacheContainer.setUmengAccountPwd(productRefKey, accountPwd);
                Log.cacheContainerObserver.info("ProdDetialObserver.onAdd:{} set UmengKey:{},AccountPwd:{} to productRefKey:{}", new Object[]{productDetial, productDetial.getThirdPartyKey(), accountPwd, productRefKey});
                break;
            //通过第三方支付Key获取产品名，产品ID
            default:
                String tpk = Constant.ReferenceKey.THIRDPARTKEY + productDetial.getThirdPartyKey();
                CacheContainer.setProductIdByTpk(tpk, productDetial.getProductId() + "");
                CacheContainer.setProductNameByTpk(tpk, productDetial.getProductName());
                Log.cacheContainerObserver.info("ProdDetialObserver.onAdd:{} set setProductIdByTpk:{},setProductNameByTpk:{} to tpk:{}", new Object[]{productDetial, productDetial.getProductId(), productDetial.getProductName(), tpk});
                break;

        }


    }

    @Override
    public void onEdit(Object object) {
        ProductDetial productDetial = (ProductDetial) object;

        String productRefKey = Constant.ReferenceKey.PRODUCTKEY + productDetial.getProductId();

        switch (productDetial.getKeyType()) {
            case Constant.KeyType.UMENGKEY:
                CacheContainer.setUmengKey(productRefKey, productDetial.getThirdPartyKey());
                String thirdPartyAccount = productDetial.getThirdPartyAccount();
                String thirdPartyPwd = productDetial.getThirdPartyPwd();

                String accountPwd = thirdPartyAccount + "|" + thirdPartyPwd;
                CacheContainer.setUmengAccountPwd(productRefKey, accountPwd);
                Log.cacheContainerObserver.info("ProdDetialObserver.onEdit:{} set UmengKey:{},AccountPwd:{} to productRefKey:{}", new Object[]{productDetial, productDetial.getThirdPartyKey(), accountPwd, productRefKey});
                break;
            //通过第三方支付Key获取产品名，产品ID
            default:
                String tpk = Constant.ReferenceKey.THIRDPARTKEY + productDetial.getThirdPartyKey();
                CacheContainer.setProductIdByTpk(tpk, productDetial.getProductId() + "");
                CacheContainer.setProductNameByTpk(tpk, productDetial.getProductName());
                Log.cacheContainerObserver.info("ProdDetialObserver.onEdit:{} set setProductIdByTpk:{},setProductNameByTpk:{} to tpk:{}", new Object[]{productDetial, productDetial.getProductId(), productDetial.getProductName(), tpk});
                break;
        }
    }

    @Override
    public void onDel(Object object) {
        ProductDetial productDetial = (ProductDetial) object;

        Log.cacheContainerObserver.info("ProdDetialObserver.onDel:{} ", productDetial);
    }
}
