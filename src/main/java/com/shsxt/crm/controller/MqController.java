package com.shsxt.crm.controller;

import com.shsxt.crm.model.ResultInfo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MqController {

    @Resource
    private AmqpTemplate amqpTemplate;

    @RequestMapping("sendMsg")
    @ResponseBody
    public ResultInfo sendMsg() {
        amqpTemplate.convertAndSend("xmjf_exchange", "mail.test", "hello spring boot mq");
        System.out.println("消息发送操作...");
        return new ResultInfo();
    }


    @RequestMapping("getMsg")
    @ResponseBody
    public ResultInfo getMsg() {
        ResultInfo resultInfo = new ResultInfo();
      /*  amqpTemplate.convertAndSend("xmjf_exchange","mail.test","hello spring boot mq");*/
        resultInfo.setResult(amqpTemplate.receiveAndConvert("xmjf_mail"));
        return resultInfo;
    }
}
