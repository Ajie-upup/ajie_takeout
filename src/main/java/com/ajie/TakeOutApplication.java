package com.ajie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author:hyj
 * @date:2022/10/1
 */

@Slf4j
@SpringBootApplication
@ServletComponentScan
//开启事务注解
@EnableTransactionManagement
public class TakeOutApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeOutApplication.class, args);
    }
}
