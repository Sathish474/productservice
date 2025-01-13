package com.sathish.productservice.config;

import com.sathish.productservice.configs.ApplicationConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = ApplicationConfigurationTest.TestConfig.class)
@ActiveProfiles("test")
public class ApplicationConfigurationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRestTemplate() {
        assert restTemplate != null;
    }

    @Test
    public void testRedisTemplate() {
        assert redisTemplate != null;
    }

    @Configuration
    static class TestConfig extends ApplicationConfiguration {
        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            return Mockito.mock(RedisConnectionFactory.class);
        }
    }
}
