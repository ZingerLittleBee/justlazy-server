package com.beeij.justlazy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 描述
 *
 * @author ye
 * @version 1.0.0, 2021-05-29
 * @since 2021-05-29 20:40
 */

@MapperScan(basePackages = "com.beeij.justlazy.mysql.dao.mapper")
@SpringBootApplication
public class JustLazyApplication {
    public static void main(String[] args) {
        SpringApplication.run(JustLazyApplication.class, args);
    }
}
