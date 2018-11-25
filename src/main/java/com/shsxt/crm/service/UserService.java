package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import com.shsxt.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/6/25.
 */
@Service
public class UserService extends BaseService<User> {
    @Resource
    private UserDao userDao;


    public UserModel loginCheck(String userName, String userPwd){
        /**
         * 1.参数校验
         *     用户名  密码 不能为空
         * 2.执行查询
         *     根据用户名查询用户记录
         * 3.判断记录  密码是否合法
         *    记录是否存在
         *    密码是否匹配
         * 4.返回登录用户信息
         */
        checkLoginParams(userName,userPwd);
        User user= userDao.queryUserByUserName(userName);
        AssertUtil.isTrue(null == user,"用户记录不存在!");
        userPwd= Md5Util.encode(userPwd);
        AssertUtil.isTrue(!(userPwd.equals(user.getUserPwd())),"密码不正确!");
        return buildUserModelInfo(user);

    }

    private UserModel buildUserModelInfo(User user) {
        UserModel userModel=new UserModel();
        userModel.setTrueName(user.getTrueName());
        userModel.setUserName(user.getUserName());
        // id 加密
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        return userModel;
    }


    /**
     * 参数合法性校验
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"密码不能为空!");
    }

    /**
     * 用户密码更新
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    public void updateUserPassword(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        /**
         * 1.参数校验
         *    userId  非空  记录必须存在
         *    oldPassword  newPassword confirmPassword 非空
         *    oldPassword 与数据库中密码值必须匹配
         *    newPassword 与 confirmPassword 必须一致
         * 2.执行更新
         *    新密码加密
         *    执行更新 判断结果
         */
        checkModifyPasswordParams(userId,oldPassword,newPassword,confirmPassword);
        User user=userDao.selectById(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(userDao.update(user)<1,"密码更新失败!");

    }

    private void checkModifyPasswordParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        User user=userDao.selectById(userId);
        AssertUtil.isTrue(null == userId || null ==user ,"用户未登录!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"原始密码不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"新密码不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"确认密码不能为空!");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))),"原始密码不正确!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"密码不一致!");
    }


    public List<Map<String,Object>> querySaleCustomerManager(){
        return userDao.querySaleCustomerManager();
    }
}
