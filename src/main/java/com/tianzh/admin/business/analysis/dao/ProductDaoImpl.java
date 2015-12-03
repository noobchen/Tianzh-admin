package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.Product;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pig on 2015-06-05.
 */
@Component
public class ProductDaoImpl extends SqlSessionTemplate implements ProductDao {

    @Autowired
    public ProductDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public void addProduct(Product product) {
        insert("product.addProduct",product);
    }

    @Override
    public List<Product> queryProduct(HashMap<String,Object> params) {
        return (List<Product>) selectList("product.queryProduct",params);
    }

    @Override
    public List<Product> queryProduct4Cooperation(HashMap<String, Object> params) {
        return (List<Product>) selectList("product.queryProduct4Cooperation",params);
    }

    @Override
    public void editProduct(Product product) {
        update("product.editProduct",product);
    }

    @Override
    public void deleteProduct(Product product) {
        delete("product.deleteProduct",product);
    }
}
