package com.shsxt.crm.controller;

import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lp on 2018/6/25.
 */
@Controller
public class MainController {
    @Resource
    private UserService userService;
    @RequestMapping("main")
    public  String main(HttpServletRequest request){
        int userId= LoginUserUtil.releaseUserIdFromCookie(request);
        User user=userService.selectById(userId);
        request.setAttribute("user",user);
        request.setAttribute("ctx",request.getContextPath());
        return "main";
    }
}
