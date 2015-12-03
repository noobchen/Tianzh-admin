package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.ProductIdentification;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-07.
 */
@Component
public class ProductIdentificationDaoImpl extends SqlSessionTemplate implements ProductIdentificationDao {

    @Autowired
    public ProductIdentificationDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public void addProductIdentification(ProductIdentification productIdentification) {
        insert("product_identification.addProductIdentification",productIdentification);
    }

    @Override
    public void editProductIdentification(ProductIdentification productIdentification) {
        update("product_identification.editProductIdentification", productIdentification);
    }

    @Override
    public void delProductIdentification(ProductIdentification productIdentification) {
        delete("product_identification.delProductIdentification",productIdentification);
    }

    @Override
    public List<ProductIdentification> queryProductIdentification(HashMap<String, Object> params) {
        return (List<ProductIdentification>) selectList("product_identification.queryProductIdentification",params);
    }

    @Override
    public List<ProductIdentification> queryProIdentiGroup(HashMap<String, Object> params) {
        return (List<ProductIdentification>) selectList("product_identification.queryProIdentiGroup",params);
    }

    @Override
    public List<ProductIdentification> queryAllProIdentiGroup() {
        return (List<ProductIdentification>) selectList("product_identification.queryAllProIdentiGroup");
    }
}
