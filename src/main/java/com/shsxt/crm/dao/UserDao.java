package com.shsxt.crm.dao;


import com.shsxt.base.BaseDao;
import com.shsxt.crm.vo.User;

import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/6/25.
 */
public interface UserDao extends BaseDao<User> {
    /**
     * 根据用户名查询用户记录
     * @param userName
     * @return
     */
    public User queryUserByUserName(String userName);


    public List<Map<String,Object>> querySaleCustomerManager();
}
