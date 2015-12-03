package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.constant.RedisKeyConstant;
import com.tianzh.admin.business.analysis.dao.ThPayProvinceDao;
import com.tianzh.admin.business.analysis.model.ThPayProvince;
import com.tianzh.admin.business.redis.RedisCache;
import com.tianzh.admin.common.util.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-09-15.
 */
@Service
public class ThPayProvinceServiceImpl implements ThPayProvinceService {

    @Autowired
    ThPayProvinceDao thPayProvinceDao;

    @Autowired
    RedisCache cache;

    @Override
    public List<ThPayProvince> queryProvinces(HashMap<String, Object> params) {
        return thPayProvinceDao.queryProvinces(params);
    }

    @Override
    public void addProvinces(List<ThPayProvince> thPayProvinces) throws Exception {
        if (thPayProvinces != null && thPayProvinces.size() != 0) {
            try {
                thPayProvinceDao.delProvinces(thPayProvinces.get(0));
                thPayProvinceDao.addProvinces(thPayProvinces);
                insertRedis(thPayProvinces);
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new Exception();
        }
    }



    private void insertRedis(List<ThPayProvince> thPayProvinces) {
        int thPayId = thPayProvinces.get(0).getThPayId();
        int provinderId = thPayProvinces.get(0).getProvinderId();
        int provinceType = thPayProvinces.get(0).getProvinceType();

        String key = RedisKeyConstant.THIRDPAYDETIALKEY + thPayId + "_" + provinderId + "_" + provinceType;
        ArrayList<String> provinces = new ArrayList<String>();

        for (ThPayProvince thPayProvince : thPayProvinces) {
            provinces.add(thPayProvince.getProvince());
        }

        cache.insert(key, JsonUtils.objectToJson(provinces));
    }
}
