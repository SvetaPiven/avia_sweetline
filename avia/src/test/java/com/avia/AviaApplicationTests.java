package com.avia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AviaApplicationTests {
    @Autowired
    private CacheManager cacheManager;

    @Test
    void contextLoads() {
    }

    @Test
    void testCache() {
        String key = "location1";
        String value = "Moscow";
        Cache cache = cacheManager.getCache("locations");
        assert cache != null;
        cache.put(key, value);
        String cachedValue = cache.get(key, String.class);
        assertEquals(value, cachedValue);
    }

}
