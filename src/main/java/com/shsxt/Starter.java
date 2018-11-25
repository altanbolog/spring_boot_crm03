package com.shsxt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.shsxt.crm.dao")
public class Starter {




    public static void main(String[] args) {
        SpringApplication.run(Starter.class);




    }
}
