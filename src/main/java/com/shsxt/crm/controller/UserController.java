package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.base.exceptions.ParamException;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/6/25.
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{


    @Resource
    private UserService userService;

    @RequestMapping("queryUserById")
    @ResponseBody
    public User queryUserById(Integer userId){
        return  userService.selectById(userId);
    }

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo userLogin(String userName,String userPwd){
        ResultInfo resultInfo=new ResultInfo();
        try {
            UserModel userModel= userService.loginCheck(userName,userPwd);
            resultInfo.setResult(userModel);
        } catch (ParamException e) {
            e.printStackTrace();
            resultInfo.setCode(e.getErrorCode());
            resultInfo.setMsg(e.getErrorMsg());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(300);
            resultInfo.setMsg("登录失败");
        }
        return resultInfo;
    }


    @RequestMapping("modifyPassword")
    @ResponseBody
    public ResultInfo modifyPassword(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword){
        ResultInfo resultInfo=new ResultInfo();
        try {
            Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
            userService.updateUserPassword(userId,oldPassword,newPassword,confirmPassword);
        } catch (ParamException e) {
            e.printStackTrace();
            resultInfo.setCode(e.getErrorCode());
            resultInfo.setMsg(e.getErrorMsg());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo.setCode(300);
            resultInfo.setMsg("密码更新失败");
        }
        return resultInfo;
    }


    @RequestMapping("login02")
    @ResponseBody
    public ResultInfo userLogin02(String userName,String userPwd){
        UserModel userModel= userService.loginCheck(userName,userPwd);
        return success(userModel);
    }

    @RequestMapping("modifyPassword02")
    @ResponseBody
    public ResultInfo modifyPassword02(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword){
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updateUserPassword(userId,oldPassword,newPassword,confirmPassword);
        return success("密码修改成功!");
    }


    @RequestMapping("querySaleCustomerManager")
    @ResponseBody
    public List<Map<String,Object>> querySaleCustomerManager(){
        return userService.querySaleCustomerManager();
    }
}
