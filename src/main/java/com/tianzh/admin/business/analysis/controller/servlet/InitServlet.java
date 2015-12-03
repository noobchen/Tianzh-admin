package com.tianzh.admin.business.analysis.controller.servlet;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.log.Log;
import com.tianzh.admin.business.analysis.model.CacheContainer;
import com.tianzh.admin.business.analysis.model.Product;
import com.tianzh.admin.business.analysis.model.ProductDetial;
import com.tianzh.admin.business.analysis.model.ProductIdentification;
import com.tianzh.admin.business.analysis.service.CacheContainerObserver;
import com.tianzh.admin.business.analysis.service.ProductDetialService;
import com.tianzh.admin.business.analysis.service.ProductIdentificationService;
import com.tianzh.admin.business.analysis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-07.
 */
@Component("InitServlet")
public class InitServlet {

    @Autowired()
    @Qualifier("ProdObserver")
    CacheContainerObserver productObserver;

    @Autowired()
    @Qualifier("ProdDetialObserver")
    CacheContainerObserver prodDetialObserver;

    @Autowired()
    @Qualifier("ProdIdentiObserver")
    CacheContainerObserver prodIdentiObserver;

    //缓存产品名称
    @Autowired
    ProductService productService;

    //缓存产品key
    @Autowired
    ProductDetialService productDetialService;

    //缓存用户名称，合作类型，出账类型，扣量
    @Autowired
    ProductIdentificationService productIdentificationService;


    public void init(ServletConfig config) throws ServletException {
        Log.initSystem.info("System begin init at:{}", System.currentTimeMillis());

        productService.registerObserver(productObserver);
        productDetialService.registerObserver(prodDetialObserver);
        productIdentificationService.registerObserver(prodIdentiObserver);


        List<Product> products = productService.queryProduct(null);

        for (Product prod : products) {
            int id = prod.getId();
            //通过ProductId获取友盟key,产品名
            String productRefKey = Constant.ReferenceKey.PRODUCTKEY + id;
            CacheContainer.setProductName(productRefKey, prod.getName());
            CacheContainer.setProductKey(productRefKey, prod.getAppId());
            Log.initSystem.info("setProductName:{} setProductKey:{} to productRefKey:{}", new Object[]{prod.getName(), prod.getAppId(), productRefKey});

            CacheContainer.setProductIdByTpk(Constant.ReferenceKey.THIRDPARTKEY + prod.getAppId(), String.valueOf(prod.getId()));
            Log.initSystem.info("setProductIdByTpk:{} to Tpk:{}", new Object[]{id, Constant.ReferenceKey.THIRDPARTKEY + prod.getAppId()});

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("productId", id);

            List<ProductDetial> productDetials = productDetialService.queryProductDetials(params);

            for (ProductDetial detial : productDetials) {
                if (detial.getKeyType() == Constant.KeyType.UMENGKEY) {
                    CacheContainer.setUmengKey(productRefKey, detial.getThirdPartyKey());
                    String thirdPartyAccount = detial.getThirdPartyAccount();
                    String thirdPartyPwd = detial.getThirdPartyPwd();

                    String accountPwd = thirdPartyAccount + "|" + thirdPartyPwd;
                    CacheContainer.setUmengAccountPwd(productRefKey, accountPwd);
                    Log.initSystem.info("setUmengKey:{},setAccountPwd:{} to productRefKey:{}", new Object[]{detial.getThirdPartyKey(), accountPwd, productRefKey});

                } else {
                    String tpk = Constant.ReferenceKey.THIRDPARTKEY + detial.getThirdPartyKey();
                    CacheContainer.setProductIdByTpk(tpk, id + "");
                    CacheContainer.setProductNameByTpk(tpk, prod.getName());
                    Log.initSystem.info("setProductIdByTpk:{},setProductNameByTpk:{} to Tpk:{}", new Object[]{id, prod.getName(), tpk});
                }
            }
        }


        List<ProductIdentification> productIdentifications = productIdentificationService.queryProductIdentification(null);

        for (ProductIdentification identification : productIdentifications) {
            //通过prodIdentification获取userId,userCompanyName
            String prodIdentiKey = identification.getAccountType().equals("3") ? identification.getProdIdentification() : "cp_" + identification.getUserId();
            prodIdentiKey = Constant.ReferenceKey.PRODUCTIDENTIFICATIONKEY + identification.getProductId() + "_" + prodIdentiKey;

            CacheContainer.setUserId(prodIdentiKey, identification.getUserId() + "");
            CacheContainer.setUserCompanyName(prodIdentiKey, identification.getUserCompanyName());

            Log.initSystem.info("setUserId:{},setUserCompanyName:{} to prodIdentiKey:{}", new Object[]{identification.getUserId(), identification.getUserCompanyName(), prodIdentiKey});


            //通过userRefKey获取discount，cooperation，chargeOffType,Sharing
//            String userRefKey = Constant.ReferenceKey.USERKEY + identification.getUserId();

            CacheContainer.setUserDiscount(prodIdentiKey, identification.getDiscount() + "");
            CacheContainer.setUserCooperationType(prodIdentiKey, identification.getCooperationType() + "");
            CacheContainer.setUserChargeOffType(prodIdentiKey, identification.getChargeOffType() + "");
            CacheContainer.setSharing(prodIdentiKey, identification.getSharing() + "");

            Log.initSystem.info("setUserDiscount:{},setUserCooperationType:{},setUserChargeOffType:{},setSharing:{} to prodIdentiKey:{}", new Object[]{identification.getDiscount(), identification.getCooperationType(), identification.getChargeOffType(), identification.getSharing(), prodIdentiKey});
        }


        Log.initSystem.info("System init success at:{}", System.currentTimeMillis());
    }
}
