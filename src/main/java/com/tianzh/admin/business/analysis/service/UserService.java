package com.tianzh.admin.business.analysis.service;

import com.tianzh.admin.business.analysis.model.ProductAnalysis;

import java.util.List;

/**
 * Created by pig on 2015-06-06.
 */
public interface UserService {

    Object obtainNewUsers(String productIdKey, String specifyDate) throws Exception;

    UmengServiceImpl.App obtainAppNewUsers(String productIdKey, String appKey, String specifyDate) throws Exception;
}
