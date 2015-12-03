package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.ProductIdentification;
import com.tianzh.admin.business.analysis.model.CacheContainer;
import org.springframework.stereotype.Component;
import sun.misc.Cache;

/**
 * Created by pig on 2015-06-07.
 */
@Component("ProdIdentiObserver")
public class ProductIdentificationObserverImpl implements CacheContainerObserver {

    @Override
    public void onAdd(Object object) {
        ProductIdentification productIdentification = (ProductIdentification) object;

        //通过prodIdentification获取discount，cooperation，chargeOffType,Sharing
        String prodIdentiKey = productIdentification.getAccountType().equals("3") ? productIdentification.getProdIdentification() : "cp_" + productIdentification.getUserId();
        prodIdentiKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productIdentification.getProductId() + "_" + prodIdentiKey;

        CacheContainer.setUserChargeOffType(prodIdentiKey, productIdentification.getChargeOffType() + "");
        CacheContainer.setUserCooperationType(prodIdentiKey, productIdentification.getCooperationType() + "");
        CacheContainer.setUserDiscount(prodIdentiKey, productIdentification.getDiscount() + "");
        CacheContainer.setSharing(prodIdentiKey, productIdentification.getSharing() + "");

        Log.cacheContainerObserver.info("ProdIdentiObserver.onAdd:{} set UserChargeOffType:{},UserCooperationType:{},UserDiscount:{},setSharing:{} to prodIdentification:{}", new Object[]{productIdentification, productIdentification.getChargeOffType(), productIdentification.getCooperationType(), productIdentification.getDiscount(), productIdentification.getSharing() + "", prodIdentiKey});

        //通过prodIdentification获取userId,userCompanyName
        CacheContainer.setUserCompanyName(prodIdentiKey, productIdentification.getUserCompanyName());
        CacheContainer.setUserId(prodIdentiKey, productIdentification.getUserId() + "");

        Log.cacheContainerObserver.info("ProdIdentiObserver.onAdd:{} set UserCompanyName:{},UserId:{} to prodIdentiKey:{}", new Object[]{productIdentification, productIdentification.getUserCompanyName(), productIdentification.getUserId(), prodIdentiKey});

    }

    @Override
    public void onEdit(Object object) {
        ProductIdentification productIdentification = (ProductIdentification) object;

        //通过prodIdentification获取discount，cooperation，chargeOffType,sharing
        String prodIdentiKey = productIdentification.getAccountType().equals("3") ? productIdentification.getProdIdentification() : "cp_" + productIdentification.getUserId();
        prodIdentiKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + productIdentification.getProductId() + "_" + prodIdentiKey;

        CacheContainer.setUserChargeOffType(prodIdentiKey, productIdentification.getChargeOffType() + "");
        CacheContainer.setUserCooperationType(prodIdentiKey, productIdentification.getCooperationType() + "");
        CacheContainer.setUserDiscount(prodIdentiKey, productIdentification.getDiscount() + "");
        CacheContainer.setSharing(prodIdentiKey, productIdentification.getSharing() + "");

        Log.cacheContainerObserver.info("ProdIdentiObserver.onEdit:{} set UserChargeOffType:{},UserCooperationType:{},UserDiscount:{},setSharing:{} to prodIdentiKey:{}", new Object[]{productIdentification, productIdentification.getChargeOffType(), productIdentification.getCooperationType(), productIdentification.getDiscount(), productIdentification.getSharing(), prodIdentiKey});

        //通过prodIdentification获取userId,userCompanyName
        CacheContainer.setUserCompanyName(prodIdentiKey, productIdentification.getUserCompanyName());
        CacheContainer.setUserId(prodIdentiKey, productIdentification.getUserId() + "");

        Log.cacheContainerObserver.info("ProdIdentiObserver.onEdit:{} set UserCompanyName:{},UserId:{} to prodIdentiKey:{}", new Object[]{productIdentification, productIdentification.getUserCompanyName(), productIdentification.getUserId(), prodIdentiKey});
    }

    @Override
    public void onDel(Object object) {
        ProductIdentification productIdentification = (ProductIdentification) object;
//
//        String userRefKey = Constant.ReferenceKey.USERKEY + productIdentification.getUserId();
//
//        CacheContainer.removeUser(userRefKey);

        Log.cacheContainerObserver.info("ProdIdentiObserver.onDel:{}", productIdentification);

    }
}
