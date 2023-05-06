package com.avia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.avia")
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableWebMvc
@EnableCaching
public class AviaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AviaApplication.class, args);
    }

}
