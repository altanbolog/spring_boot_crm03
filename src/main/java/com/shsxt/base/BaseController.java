package com.shsxt.base;

import com.shsxt.crm.model.ResultInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lp on 2018/6/22.
 */
public class BaseController {


    @ModelAttribute
    public  void init(HttpServletRequest request){
        System.out.println("在子类方法执行前执行该方法");
        request.setAttribute("ctx",request.getContextPath());
    }

    public ResultInfo success(String msg){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public ResultInfo success(String msg,Object result){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }

    public ResultInfo success(Object result){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setResult(result);
        return resultInfo;
    }

}
