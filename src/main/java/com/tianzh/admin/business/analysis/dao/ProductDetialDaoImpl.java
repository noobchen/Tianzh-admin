package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.FeePoint;
import com.tianzh.admin.business.analysis.model.ProductDetial;
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
public class ProductDetialDaoImpl extends SqlSessionTemplate implements ProductDetialDao {

    @Autowired
    public ProductDetialDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public void addProductDetial(ProductDetial detial) {
        insert("product_detial.addProductDetial", detial);
    }

    @Override
    public void delProductDetial(ProductDetial detial) {
        delete("product_detial.delProductDetial", detial);
    }

    @Override
    public void editProductDetial(ProductDetial detial) {
        update("product_detial.editProductDetial", detial);
    }

    @Override
    public List<ProductDetial> queryProductDetials(HashMap<String, Object> params) {
        return (List<ProductDetial>) selectList("product_detial.queryProductDetials", params);
    }




    @Override
    public void addFeePointInfo(FeePoint feePoint) {
        insert("product_detial.addFeePointInfo", feePoint);
    }

    @Override
    public void addFeePointLetu(FeePoint feePoint) {
        insert("product_detial.addFeePointLetu", feePoint);
    }

    @Override
    public void addFeePointYL(FeePoint feePoint) {
        insert("product_detial.addFeePointYL", feePoint);

    }

    @Override
    public void addFeePointZhang(FeePoint feePoint) {
        insert("product_detial.addFeePointZhang", feePoint);

    }

    @Override
    public List<FeePoint> queryFeePointInfo(HashMap<String, Object> params) {

        return (List<FeePoint>) selectList("product_detial.queryFeePointInfo", params);
    }

    @Override
    public FeePoint queryFeePointLetu(HashMap<String, Object> params) {
        return (FeePoint) selectOne("product_detial.queryFeePointLetu", params);
    }

    @Override
    public FeePoint queryFeePointYL(HashMap<String, Object> params) {
        return (FeePoint) selectOne("product_detial.queryFeePointYL", params);

    }

    @Override
    public FeePoint queryFeePointZhang(HashMap<String, Object> params) {
        return (FeePoint) selectOne("product_detial.queryFeePointZhang", params);

    }

    @Override
    public void editFeePointInfo(FeePoint feePoint) {
        update("product_detial.editFeePointInfo", feePoint);
    }

    @Override
    public void editFeePointLetu(FeePoint feePoint) {
        update("product_detial.editFeePointLetu", feePoint);

    }

    @Override
    public void editFeePointYL(FeePoint feePoint) {
        update("product_detial.editFeePointYL", feePoint);

    }

    @Override
    public void editFeePointZhang(FeePoint feePoint) {
        update("product_detial.editFeePointZhang", feePoint);

    }

    @Override
    public void delFeePointInfo(FeePoint feePoint) {
        delete("product_detial.delFeePointInfo", feePoint);
    }

    @Override
    public void delFeePointLetu(FeePoint feePoint) {
        delete("product_detial.delFeePointLetu", feePoint);

    }

    @Override
    public void delFeePointYL(FeePoint feePoint) {
        delete("product_detial.delFeePointYL", feePoint);

    }

    @Override
    public void delFeePointZhang(FeePoint feePoint) {
        delete("product_detial.delFeePointZhang", feePoint);

    }
}
