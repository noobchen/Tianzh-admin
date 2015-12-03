package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.model.ThPayProvince;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-09-15.
 */
public interface ThPayProvinceService {
    List<ThPayProvince> queryProvinces(HashMap<String,Object> params);

    void addProvinces(List<ThPayProvince> thPayProvinces) throws Exception;


}
