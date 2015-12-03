package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.ThPay;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pig on 2015-09-15.
 */
@Component
public class ThPayDaoImpl extends SqlSessionTemplate implements ThPayDao {

    @Autowired
    public ThPayDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public void addThPay(ThPay thPay) {
        insert("th_pay.addThPay",thPay);
    }

    @Override
    public void editThPay(ThPay thPay) {
        update("th_pay.editThPay",thPay);
    }

    @Override
    public List<ThPay> queryThPay() {
        return (List<ThPay>) selectList("th_pay.queryThPay");
    }

    @Override
    public void delThPay(ThPay thPay) {
        delete("th_pay.delThPay", thPay);
    }
}
