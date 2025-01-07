package com.sathish.productservice.configs;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Custom health check logic
        boolean isHealthy = checkCustomHealth();
        if (isHealthy) {
            return Health.up().withDetail("Custom Health Check", "Service is healthy").build();
        } else {
            return Health.down().withDetail("Custom Health Check", "Service is not healthy").build();
        }
    }

    private boolean checkCustomHealth() {
        // Implement your custom health check logic here
        return true;
    }
}