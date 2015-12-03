package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.ThPayProvince;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-09-15.
 */
@Component
public class ThPayProvinceDaoImpl extends SqlSessionTemplate implements ThPayProvinceDao {

    @Autowired
    public ThPayProvinceDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public List<ThPayProvince> queryProvinces(HashMap<String,Object> params) {
        return (List<ThPayProvince>) selectList("thpay_province.queryProvinces", params);
    }

    @Override
    public void delProvinces(ThPayProvince thPayProvinces) {
        delete("thpay_province.delProvinces", thPayProvinces);
    }

    @Override
    public void addProvinces(List<ThPayProvince> thPayProvinces) {
        insert("thpay_province.addProvinces", thPayProvinces);
    }


}
