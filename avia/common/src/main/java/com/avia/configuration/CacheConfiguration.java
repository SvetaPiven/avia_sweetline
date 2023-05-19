package com.avia.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "caffeine")
public class CacheConfiguration {

    private Integer initialCapacity;

    private Long maximumSize;

    private Long expireAfterAccessDays;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("c_airlines", "c_document_type",
                "c_flight_status", "c_plane_types", "c_roles", "c_ticket_status", "users", "c_ticket_class");
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }

    public Caffeine<Object, Object> cacheProperties() {
        return Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccessDays, TimeUnit.DAYS)
                .weakKeys()
                .recordStats();
    }
}