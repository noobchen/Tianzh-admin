package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.constant.RedisKeyConstant;
import com.tianzh.admin.business.analysis.dao.ThPayDao;
import com.tianzh.admin.business.analysis.model.ThPay;
import com.tianzh.admin.business.analysis.model.ThPayProperty;
import com.tianzh.admin.business.redis.RedisCache;
import com.tianzh.admin.common.util.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pig on 2015-09-15.
 */
@Service
public class ThPayServiceImpl implements ThPayService {
    @Autowired
    ThPayDao thPayDao;

    @Autowired
    RedisCache cache;

    @Override
    public void addThPay(ThPay thPay) throws Exception {
        try {
            thPayDao.addThPay(thPay);
            insertRedis();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void editThPay(ThPay thPay) throws Exception {
        try {
            thPayDao.editThPay(thPay);
            insertRedis();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ThPay> queryThPay() {
        return thPayDao.queryThPay();
    }

    @Override
    public void delThPay(ThPay thPay) throws Exception {
        try {
            thPayDao.delThPay(thPay);
            insertRedis();
        } catch (Exception e) {
            throw e;
        }
    }


    private void insertRedis() {
        List<ThPay> thPays = queryThPay();
        List<ThPayProperty> thPayProperties = new ArrayList<ThPayProperty>();

        for (ThPay thPay : thPays) {
            if (thPay.getStatus() == 0) {
                ThPayProperty thPayProperty = new ThPayProperty();

                thPayProperty.setThPayId(String.valueOf(thPay.getId()));
                thPayProperty.setWeight(thPay.getWeight());

                thPayProperties.add(thPayProperty);
            }
        }

        if (thPayProperties.size() > 0) {
            String json = JsonUtils.objectToJson(thPayProperties);

            if (json != null) cache.insert(RedisKeyConstant.TOTALTHIRDPAYKEY, json);
        }
    }
}
