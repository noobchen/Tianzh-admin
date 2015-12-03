package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.ThPayProvince;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-09-15.
 */
public interface ThPayProvinceDao {

    List<ThPayProvince> queryProvinces(HashMap<String,Object> params);

    void delProvinces(ThPayProvince thPayProvinces);

    void addProvinces(List<ThPayProvince> thPayProvinces);


}
