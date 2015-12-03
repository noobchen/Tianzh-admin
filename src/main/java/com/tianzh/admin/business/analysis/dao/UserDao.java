package com.tianzh.admin.business.analysis.dao;

import java.util.HashMap;

/**
 * Created by pig on 2015-10-08.
 */
public interface UserDao {

    Integer queryUseFullUsers(HashMap<String,Object> params);
}
