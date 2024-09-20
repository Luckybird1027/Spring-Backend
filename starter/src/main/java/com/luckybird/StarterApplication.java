package com.luckybird;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * SpringBoot 启动类
 *
 * @author 新云鸟
 */
@SpringBootApplication
@EnableAsync
public class StarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
        System.out.println("Application is running at http://localhost:8080");
    }

}
