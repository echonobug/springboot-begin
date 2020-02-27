package com.jw.springbootbegin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jw.springbootbegin.mapper")
public class SpringbootBeginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBeginApplication.class, args);
    }

}
