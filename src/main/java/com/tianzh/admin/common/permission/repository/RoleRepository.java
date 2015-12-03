package com.tianzh.admin.common.permission.repository;

import com.tianzh.admin.common.page.PageInfo;
import com.tianzh.admin.common.permission.model.Resource;
import com.tianzh.admin.common.permission.model.Role;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyc
 * Date: 12-9-10
 * Time: 下午8:24
 * To change this template use File | Settings | File Templates.
 */
public interface RoleRepository {
    public PageInfo query(PageInfo pageInfo, HashMap<String, Object> queryParams);

    public List<Role> query(HashMap<String, Object> queryParams);

    public List<Resource> queryResource(Integer roleId);

    public Role find(Integer id);

    public void add(Role role);

    public void edit(Role role);

    public void delete(Role role);
}
