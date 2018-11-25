package com.shsxt.crm.interceptors;

import com.shsxt.crm.constant.CrmConstant;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lp on 2018/6/25.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 实现思路
         *    判断cookie 中用户id 是否存在
         */
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        /*AssertUtil.isTrue(null == userId || null ==userService.selectById(userId),CrmConstant.NOT_LOGIN_CODE, CrmConstant.NOT_LOGIN_MSG);*/
        AssertUtil.isNotLogin(null == userId || null ==userService.selectById(userId),CrmConstant.NOT_LOGIN_MSG);
        return true;
    }
}
