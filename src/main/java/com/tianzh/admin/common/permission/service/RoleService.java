package com.tianzh.admin.common.permission.service;

import com.tianzh.admin.common.page.PageInfo;
import com.tianzh.admin.common.permission.model.Resource;
import com.tianzh.admin.common.permission.model.Role;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyc
 * Date: 12-9-10
 * Time: 下午10:08
 * To change this template use File | Settings | File Templates.
 */
public interface RoleService {
    public PageInfo query(PageInfo pageInfo, HashMap<String, Object> queryParams);

    public List<Role> query(HashMap<String, Object> queryParams);

    public List<Resource> queryResource(Integer roleId);

    public String add(Role role);

    public String edit(Role role);

    public String delete(Role role);
}
