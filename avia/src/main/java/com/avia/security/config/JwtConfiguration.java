package com.avia.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("config")
public class JwtConfiguration {

    @Value("${config.secret}")
    private String secret;

    @Value("${config.expiration}")
    private Integer expiration;
}