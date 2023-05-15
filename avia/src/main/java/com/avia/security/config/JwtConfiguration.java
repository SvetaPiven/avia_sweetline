package com.avia.security.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@NoArgsConstructor
@Configuration
@Setter
@Getter
@ConfigurationProperties("config")
public class JwtConfiguration {

    @Value("${config.secret}")
    private String secret;

    private Integer expiration;

}
