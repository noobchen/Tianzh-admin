package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.constant.Constant;
import com.tianzh.admin.business.analysis.constant.RedisKeyConstant;
import com.tianzh.admin.business.analysis.dao.ProductDetialDao;
import com.tianzh.admin.business.analysis.model.CacheContainer;
import com.tianzh.admin.business.analysis.model.FeePoint;
import com.tianzh.admin.business.analysis.model.ProductDetial;
import com.tianzh.admin.business.redis.RedisCache;
import com.tianzh.admin.common.page.PageInfo;
import com.tianzh.admin.common.page.PageUtils;
import com.tianzh.admin.common.util.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
@Component
public class ProductDetialServiceImpl implements ProductDetialService {
    @Autowired
    ProductDetialDao productDetialDao;

    @Autowired
    RedisCache cache;

    @Override
    public void addProductDetial(ProductDetial detial) throws Exception {
        try {
            productDetialDao.addProductDetial(detial);

            for (CacheContainerObserver observer : observers) {
                observer.onAdd(detial);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delProductDetial(ProductDetial detial) throws Exception {
        try {
            productDetialDao.delProductDetial(detial);

            for (CacheContainerObserver observer : observers) {
                observer.onDel(detial);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void editProductDetial(ProductDetial detial) throws Exception {
        try {
            productDetialDao.editProductDetial(detial);

            for (CacheContainerObserver observer : observers) {
                observer.onEdit(detial);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PageInfo queryProductDetials(PageInfo pageInfo, HashMap<String, Object> params) {
        params.put("pageInfo", pageInfo);
        List<ProductDetial> productDetials = productDetialDao.queryProductDetials(params);
        Integer productId = (Integer) params.get("productId");
        String productName = CacheContainer.getProductName(Constant.ReferenceKey.PRODUCTKEY + productId);

        for (ProductDetial detial : productDetials) {
            detial.setProductName(productName);
        }
        pageInfo.setResult(productDetials);

        return pageInfo;
    }

    @Override
    public List<ProductDetial> queryProductDetials(HashMap<String, Object> params) {
        return productDetialDao.queryProductDetials(params);
    }

    @Override
    public void registerObserver(CacheContainerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void addFeePointInfo(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.addFeePointInfo(feePoint);
            insertFeePoint2Redis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void addFeePointLetu(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.addFeePointLetu(feePoint);
            insertLt2Redis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void addFeePointYL(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.addFeePointYL(feePoint);
            insertYl2Redis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void addFeePointZhang(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.addFeePointZhang(feePoint);
            insertZh2Redis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<FeePoint> queryFeePointInfo(HashMap<String, Object> params) {
        return productDetialDao.queryFeePointInfo(params);
    }

    @Override
    public FeePoint queryFeePointLetu(HashMap<String, Object> params) {
        return productDetialDao.queryFeePointLetu(params);
    }

    @Override
    public FeePoint queryFeePointYL(HashMap<String, Object> params) {
        return productDetialDao.queryFeePointYL(params);
    }

    @Override
    public FeePoint queryFeePointZhang(HashMap<String, Object> params) {
        return productDetialDao.queryFeePointZhang(params);
    }

    @Override
    public void editFeePointInfo(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.editFeePointInfo(feePoint);
            insertFeePoint2Redis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void editFeePointLetu(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.editFeePointLetu(feePoint);
            insertLt2Redis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void editFeePointYL(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.editFeePointYL(feePoint);
            insertYl2Redis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void editFeePointZhang(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.editFeePointZhang(feePoint);
            insertZh2Redis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delFeePointInfo(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.delFeePointInfo(feePoint);
            delFeePointFromRedis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delFeePointLetu(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.delFeePointLetu(feePoint);
            delLtFromRedis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delFeePointYL(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.delFeePointYL(feePoint);
            delYlFromRedis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delFeePointZhang(FeePoint feePoint) throws Exception {
        try {
            productDetialDao.delFeePointZhang(feePoint);
            delZhFromRedis(feePoint);
        } catch (Exception e) {
            throw e;
        }
    }

    private void insertFeePoint2Redis(FeePoint feePoint) {
        String key = RedisKeyConstant.PRODUCTDETIALKEY_ + feePoint.getFeePointId();

        cache.insert(key, JsonUtils.objectToJson(feePoint));
    }

    private void delFeePointFromRedis(FeePoint feePoint) {
        String key = RedisKeyConstant.PRODUCTDETIALKEY_ + feePoint.getFeePointId();

        cache.del(key);
    }

    private void insertLt2Redis(FeePoint feePoint) {
        String key = RedisKeyConstant.PRODUCTDETIALKEY_ + feePoint.getFeePointId()+ "_" + "1";

        cache.insert(key, JsonUtils.objectToJson(feePoint));
    }

    private void delLtFromRedis(FeePoint feePoint) {
        String key = RedisKeyConstant.PRODUCTDETIALKEY_ + feePoint.getFeePointId() + "_" + "1";

        cache.del(key);
    }

    private void insertYl2Redis(FeePoint feePoint) {
        String key = RedisKeyConstant.PRODUCTDETIALKEY_ + feePoint.getFeePointId()+ "_" + "2";

        cache.insert(key, JsonUtils.objectToJson(feePoint));
    }

    private void delYlFromRedis(FeePoint feePoint) {
        String key = RedisKeyConstant.PRODUCTDETIALKEY_ + feePoint.getFeePointId() + "_" + "2";

        cache.del(key);
    }

    private void insertZh2Redis(FeePoint feePoint) {
        String key = RedisKeyConstant.PRODUCTDETIALKEY_ + feePoint.getFeePointId()+ "_" + "3";

        cache.insert(key, JsonUtils.objectToJson(feePoint));
    }

    private void delZhFromRedis(FeePoint feePoint) {
        String key = RedisKeyConstant.PRODUCTDETIALKEY_ + feePoint.getFeePointId() + "_" + "3";

        cache.del(key);
    }
}
