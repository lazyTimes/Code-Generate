package com.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //exclude 关闭数据源的自动配置
public class CodeGenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeGenerateApplication.class, args);
    }

}
