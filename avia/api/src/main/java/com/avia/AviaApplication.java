package com.avia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.avia")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@EnableCaching
@EnableWebMvc
public class AviaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AviaApplication.class, args);
    }

}
