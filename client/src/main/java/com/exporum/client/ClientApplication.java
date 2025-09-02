package com.exporum.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */


@SpringBootApplication
@ComponentScan(basePackages = {"com.exporum.client", "com.exporum.core"})
@MapperScan(basePackages = {"com.exporum.client", "com.exporum.core"})
@EnableCaching
public class ClientApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ClientApplication.class);
    }
}
