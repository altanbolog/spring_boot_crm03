package com.shsxt.crm.resolvers;

import com.alibaba.fastjson.JSON;
import com.shsxt.base.exceptions.AuthException;
import com.shsxt.base.exceptions.ParamException;
import com.shsxt.crm.constant.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by lp on 2018/6/25.
 *  实现异常统一处理
 */
//@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        /**
         * 异常分类处理
         *   错误视图
         *   异常json消息
         */


        /**
         * 首先判断是否登录
         */
     /*   if(ex instanceof AuthException){
            try {
                //可能会出现 登录页面被包含到主页面
                response.sendRedirect(request.getContextPath()+"/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }*/


        if(ex instanceof AuthException){
           ModelAndView mv=getDefultModelAndView();
           mv.setViewName("not_login");
           mv.addObject("ctx",request.getContextPath());
           mv.addObject("msg",((AuthException) ex).getErrorMsg());
           // 设置访问的uri
            mv.addObject("uri",request.getRequestURI());
           return mv;
        }



       if(handler instanceof  HandlerMethod){
           HandlerMethod handlerMethod= (HandlerMethod) handler;
           Method method=handlerMethod.getMethod();// 目标方法
           ResponseBody responseBody= method.getAnnotation(ResponseBody.class);
           if(null !=responseBody){
               /**
                * 当前方法相应内容为json
                */
               ResultInfo resultInfo=new ResultInfo();
               // 设置默认错误消息
               resultInfo.setCode(300);
               resultInfo.setMsg("操作失败!");
               if(ex instanceof ParamException){
                   ParamException pe= (ParamException) ex;
                   /*if(pe.getErrorCode().equals(CrmConstant.NOT_LOGIN_CODE)){
                       try {
                           response.sendRedirect(request.getContextPath()+"/index");
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       return null;
                   }*/
                   resultInfo.setCode(pe.getErrorCode());
                   resultInfo.setMsg(pe.getErrorMsg());
               }
               /**
                * 如果应用程序有其他类型异常  加入if 即可
                */

               /**
                * 将resultInfo 对象写出到浏览器 格式为:json串
                */
               response.setCharacterEncoding("utf-8");
               response.setContentType("application/json;charset=utf-8");
               PrintWriter pw=null;
               try {
                   pw=response.getWriter();
                   pw.write(JSON.toJSONString(resultInfo));
                   pw.flush();
               } catch (IOException e) {
                   e.printStackTrace();
               }finally {
                   if(null !=pw){
                       pw.close();
                   }
               }

               return null;
           }else{
               /**
                * 当前方法相应内容为视图
                */
               ModelAndView mv=getDefultModelAndView();
               if(ex instanceof ParamException){
                   ParamException pe= (ParamException) ex;
                  /* if(pe.getErrorCode().equals(CrmConstant.NOT_LOGIN_CODE)){
                       try {
                           response.sendRedirect(request.getContextPath()+"/index");
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       return null;
                   }*/
                   mv.addObject("code",pe.getErrorCode());
                   mv.addObject("msg",pe.getErrorMsg());
               }
               return mv;

           }
       }else{
           return getDefultModelAndView();
       }

    }

    /**
     * 默认错误视图与错误消息
     * @return
     */
    public ModelAndView getDefultModelAndView() {
        ModelAndView mv= new ModelAndView("error");
        mv.addObject("code",300);
        mv.addObject("msg","操作失败!");
        return mv;
    }
}
