package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.model.ThPay;

import java.util.List;

/**
 * Created by pig on 2015-09-15.
 */
public interface ThPayService {

    void addThPay(ThPay thPay) throws Exception;

    void editThPay(ThPay thPay) throws Exception;

    List<ThPay> queryThPay();

    void delThPay(ThPay thPay) throws Exception;
}
