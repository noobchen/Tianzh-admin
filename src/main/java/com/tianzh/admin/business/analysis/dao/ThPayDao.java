package com.tianzh.admin.business.analysis.dao;

import com.tianzh.admin.business.analysis.model.ThPay;

import java.util.List;

/**
 * Created by pig on 2015-09-15.
 */
public interface ThPayDao {

    void addThPay(ThPay thPay);

    void editThPay(ThPay thPay);

    List<ThPay> queryThPay();

    void delThPay(ThPay thPay);
}
