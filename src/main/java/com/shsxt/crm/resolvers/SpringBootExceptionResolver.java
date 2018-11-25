package com.shsxt.crm.resolvers;

import com.shsxt.base.exceptions.ParamException;
import com.shsxt.crm.constant.CrmConstant;
import com.shsxt.crm.model.ResultInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SpringBootExceptionResolver {



    @ExceptionHandler(value = {ParamException.class})
    @ResponseBody
    public ResultInfo paramExceptionResolver(ParamException e){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setCode(e.getErrorCode());
        resultInfo.setMsg(e.getErrorMsg());
        return resultInfo;
    }




    @ExceptionHandler(value = {ArithmeticException.class})
    @ResponseBody
    public ResultInfo arithmeticExceptionResolver(ArithmeticException e){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setCode(CrmConstant.OPS_FAILED_CODE);
        resultInfo.setMsg("被除数不能为0异常!");
        return resultInfo;
    }


    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResultInfo exceptionResolver(Exception e){
        ResultInfo resultInfo=new ResultInfo();
        resultInfo.setCode(CrmConstant.OPS_FAILED_CODE);
        resultInfo.setMsg(CrmConstant.OPS_FAILED_MSG);
        return resultInfo;
    }
}
