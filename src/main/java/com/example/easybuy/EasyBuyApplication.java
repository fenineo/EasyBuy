package com.example.easybuy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//扫描mapper接口
@MapperScan(basePackages = "com.example.easybuy.mapper")
public class EasyBuyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyBuyApplication.class, args);
    }

}
