package com.tianzh.admin.business.analysis.model;

import com.tianzh.admin.business.analysis.constant.Constant;

import java.util.HashMap;

/**
 * Created by pig on 2015-06-07.
 */
public class CacheContainer {
    //String：ProductRefKey，HashMap<String,String>：key:productName,productDetialKeys
    public static HashMap<String, HashMap<String, String>> productInfo = new HashMap<String, HashMap<String, String>>();
    //String：userRefKey，HashMap<String,String>：key:productName,productDetialKeys
    public static HashMap<String, HashMap<String, String>> userInfo = new HashMap<String, HashMap<String, String>>();

    //Junit测试
    /*static {
        String key1 = Constant.ReferenceKey.PRODUCTKEY+"2";
        String key2 = Constant.ReferenceKey.PRODUCTKEY+"3";

        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put(Constant.ReferenceKey.UMENGKEY,"55304659fd98c545850008b5");
        productInfo.put(key1,map1);

        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put(Constant.ReferenceKey.UMENGKEY,"5562f26567e58e76b500347b");
        productInfo.put(key2,map2);

    }*/

    public static void removeProduct(String productRefKey){
        productInfo.remove(productRefKey);
    }

    public static void removeUser(String userRefKey){
        userInfo.remove(userRefKey);
    }


    //通过第三方支付key获取产品名，产品ID
    public static String getProductNameByTpk(String tpk) {
        HashMap<String, String> productAttr = productInfo.get(tpk);
        return productAttr.get(Constant.ReferenceKey.PRODUCTNAMEKEY);
    }

    public static void setProductNameByTpk(String tpk, String productName) {
        HashMap<String, String> productAttr = productInfo.get(tpk);

        if (productAttr == null) {
            productAttr = new HashMap<String, String>();
            productInfo.put(tpk,productAttr);
        }

        productAttr.put(Constant.ReferenceKey.PRODUCTNAMEKEY, productName);
    }

    public static String getProductIdByTpk(String tpk) {
        HashMap<String, String> productAttr = productInfo.get(tpk);
        return productAttr.get(Constant.ReferenceKey.PRODUCTIDKEY);
    }

    public static void setProductIdByTpk(String tpk, String productId) {
        HashMap<String, String> productAttr = productInfo.get(tpk);

        if (productAttr == null) {
            productAttr = new HashMap<String, String>();
            productInfo.put(tpk,productAttr);
        }

        productAttr.put(Constant.ReferenceKey.PRODUCTIDKEY, productId);
    }



    //通过ProductId获取友盟key，账号密码,产品名
    public static String getUmengKey(String productRefKey) {
        HashMap<String, String> productAttr = productInfo.get(productRefKey);
        return productAttr.get(Constant.ReferenceKey.UMENGKEY);
    }

    public static void setUmengKey(String productRefKey, String umengKey) {
        HashMap<String, String> productAttr = productInfo.get(productRefKey);

        if (productAttr == null) {
            productAttr = new HashMap<String, String>();
            productInfo.put(productRefKey,productAttr);
        }

        productAttr.put(Constant.ReferenceKey.UMENGKEY, umengKey);
    }

    public static String getUmengAccountPwd(String productRefKey) {
        HashMap<String, String> productAttr = productInfo.get(productRefKey);
        return productAttr.get(Constant.ReferenceKey.UMENGACCOUNTPWDKEY);
    }

    public static void setUmengAccountPwd(String productRefKey, String umengAccountPwd) {
        HashMap<String, String> productAttr = productInfo.get(productRefKey);

        if (productAttr == null) {
            productAttr = new HashMap<String, String>();
            productInfo.put(productRefKey,productAttr);
        }

        productAttr.put(Constant.ReferenceKey.UMENGACCOUNTPWDKEY, umengAccountPwd);
    }


    public static String getProductName(String productRefKey) {
        HashMap<String, String> productAttr = productInfo.get(productRefKey);
        return productAttr.get(Constant.ReferenceKey.PRODUCTNAMEKEY);
    }

    public static void setProductName(String productRefKey, String productName) {
        HashMap<String, String> productAttr = productInfo.get(productRefKey);

        if (productAttr == null) {
            productAttr = new HashMap<String, String>();
            productInfo.put(productRefKey,productAttr);
        }

        productAttr.put(Constant.ReferenceKey.PRODUCTNAMEKEY, productName);

    }

//    public static String getProductId(String productRefKey) {
//        HashMap<String, String> productAttr = productInfo.get(productRefKey);
//        return productAttr.get(Constant.ReferenceKey.PRODUCTAPPKEY);
//    }
//
//    public static void setProductId(String productRefKey, String productId) {
//        HashMap<String, String> productAttr = productInfo.get(productRefKey);
//
//        if (productAttr == null) {
//            productAttr = new HashMap<String, String>();
//            productInfo.put(productRefKey,productAttr);
//        }
//
//        productAttr.put(Constant.ReferenceKey.PRODUCTAPPKEY, productId);
//
//    }


    public static String getProductKey(String productRefKey) {
        HashMap<String, String> productAttr = productInfo.get(productRefKey);
        return productAttr.get(Constant.ReferenceKey.PRODUCTKEYKEY);
    }

    public static void setProductKey(String productRefKey, String productKey) {
        HashMap<String, String> productAttr = productInfo.get(productRefKey);

        if (productAttr == null) {
            productAttr = new HashMap<String, String>();
            productInfo.put(productRefKey,productAttr);
        }

        productAttr.put(Constant.ReferenceKey.PRODUCTKEYKEY, productKey);

    }

    //通过prodIdentification获取userId,userCompanyName
    public static String getUserCompanyName(String prodIdentiKey) {
        HashMap<String, String> productAttr = userInfo.get(prodIdentiKey);
        return productAttr.get(Constant.ReferenceKey.USERCOMPANYNAMEKEY);
    }

    public static void setUserCompanyName(String prodIdentiKey, String companyName) {
        HashMap<String, String> userAttr = userInfo.get(prodIdentiKey);

        if (userAttr == null) {
            userAttr = new HashMap<String, String>();
            userInfo.put(prodIdentiKey,userAttr);
        }

        userAttr.put(Constant.ReferenceKey.USERCOMPANYNAMEKEY, companyName);
    }


    public static String getUserId(String prodIdentiKey) {
        HashMap<String, String> productAttr = productInfo.get(prodIdentiKey);
        return productAttr.get(Constant.ReferenceKey.USERIDKEY);
    }

    public static void setUserId(String prodIdentiKey, String userId) {
        HashMap<String, String> productAttr = productInfo.get(prodIdentiKey);

        if (productAttr == null) {
            productAttr = new HashMap<String, String>();
            productInfo.put(prodIdentiKey,productAttr);
        }

        productAttr.put(Constant.ReferenceKey.USERIDKEY, userId);
    }



    //通过prodIdentification获取discount，cooperation，chargeOffType，sharing
    public static String getUserDiscount(String prodIdentiKey) {
        HashMap<String, String> productAttr = userInfo.get(prodIdentiKey);
        return productAttr.get(Constant.ReferenceKey.USERDISCOUNTKEY);
    }

    public static void setUserDiscount(String prodIdentiKey, String discount) {
        HashMap<String, String> userAttr = userInfo.get(prodIdentiKey);

        if (userAttr == null) {
            userAttr = new HashMap<String, String>();
            userInfo.put(prodIdentiKey,userAttr);
        }

        userAttr.put(Constant.ReferenceKey.USERDISCOUNTKEY,discount);
    }

    public static String getUserCooperationType(String prodIdentiKey) {
        HashMap<String, String> productAttr = userInfo.get(prodIdentiKey);
        return productAttr.get(Constant.ReferenceKey.USERCOOPERATIONTYPEKEY);
    }

    public static void setUserCooperationType(String prodIdentiKey, String cooperationType) {
        HashMap<String, String> userAttr = userInfo.get(prodIdentiKey);

        if (userAttr == null) {
            userAttr = new HashMap<String, String>();
            userInfo.put(prodIdentiKey,userAttr);
        }

        userAttr.put(Constant.ReferenceKey.USERCOOPERATIONTYPEKEY,cooperationType);
    }

    public static String getUserChargeOffType(String prodIdentiKey) {
        HashMap<String, String> productAttr = userInfo.get(prodIdentiKey);
        return productAttr.get(Constant.ReferenceKey.USERCHARGEOFFTYPEKEY);
    }

    public static void setUserChargeOffType(String prodIdentiKey, String chargeOffType) {
        HashMap<String, String> userAttr = userInfo.get(prodIdentiKey);

        if (userAttr == null) {
            userAttr = new HashMap<String, String>();
            userInfo.put(prodIdentiKey,userAttr);
        }

        userAttr.put(Constant.ReferenceKey.USERCHARGEOFFTYPEKEY,chargeOffType);
    }


    public static String getSharing(String prodIdentiKey) {
        HashMap<String, String> productAttr = userInfo.get(prodIdentiKey);
        return productAttr.get(Constant.ReferenceKey.USERSHARINGKEY);
    }

    public static void setSharing(String prodIdentiKey, String sharing) {
        HashMap<String, String> userAttr = userInfo.get(prodIdentiKey);

        if (userAttr == null) {
            userAttr = new HashMap<String, String>();
            userInfo.put(prodIdentiKey,userAttr);
        }

        userAttr.put(Constant.ReferenceKey.USERSHARINGKEY,sharing);
    }



}
