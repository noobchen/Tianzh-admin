package com.tianzh.admin.business.analysis.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by pig on 2015-10-08.
 */
@Component
public class UserDaoImpl extends SqlSessionTemplate implements UserDao {

    @Autowired
    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Integer queryUseFullUsers(HashMap<String, Object> params) {
        return (Integer) selectOne("user.queryUseFullUsers", params);
    }
}
