package com.shsxt.crm.proxy;

import com.alibaba.fastjson.JSON;
import com.shsxt.crm.annotations.Log;
import com.shsxt.crm.dao.LogMapper;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.LogWithBLOBs;
import com.shsxt.crm.vo.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 日志收集切面类
 */
@Component
@Aspect
public class LogCollection {


    @Autowired
    private HttpServletRequest request;

    @Resource
    private UserDao userDao;

    @Autowired
    private LogMapper logMapper;


    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.shsxt.crm.annotations.Log)")
    public  void cut(){}




    @Around(value = "cut()&&@annotation(log)")
    public Object around(ProceedingJoinPoint pjp, Log log) throws Throwable {
        Object result=null;
        LogWithBLOBs logWithBLOBs=new LogWithBLOBs();

        /**
         * 收集请求参数
         */
        logWithBLOBs.setParams(JSON.toJSONString(pjp.getArgs()));
        logWithBLOBs.setCreateDate(new Date());
        int userId= LoginUserUtil.releaseUserIdFromCookie(request);
        User user=userDao.selectById(userId);
        logWithBLOBs.setCreateMan(user.getTrueName());
        logWithBLOBs.setDescription(log.module()+"-"+log.desc());
        long startTime=System.currentTimeMillis();
        result= pjp.proceed();
        logWithBLOBs.setMethod(pjp.getSignature().getName());
        logWithBLOBs.setRequestIp(request.getRemoteHost());
        logWithBLOBs.setType("0");
        long endTime=System.currentTimeMillis();
        logWithBLOBs.setExecuteTime((int) ((endTime-startTime)/1000));
        logWithBLOBs.setResult(JSON.toJSONString(result));
        System.out.println(logWithBLOBs);
        logMapper.insert(logWithBLOBs);
        return result;

    }





}
