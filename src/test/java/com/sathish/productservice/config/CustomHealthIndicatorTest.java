package com.sathish.productservice.config;

import com.sathish.productservice.configs.CustomHealthIndicator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CustomHealthIndicatorTest {

    @Test
    void testHealthWhenServiceIsHealthy() {
        // Arrange
        CustomHealthIndicator healthIndicator = spy(new CustomHealthIndicator());
        doReturn(true).when(healthIndicator).checkCustomHealth();

        // Act
        Health health = healthIndicator.health();

        // Assert
        assertThat(health.getStatus().getCode()).isEqualTo("UP");
        assertThat(health.getDetails().get("Custom Health Check")).isEqualTo("Service is healthy");
    }

    @Test
    void testHealthWhenServiceIsUnhealthy() {
        // Arrange
        CustomHealthIndicator healthIndicator = spy(new CustomHealthIndicator());
        doReturn(false).when(healthIndicator).checkCustomHealth();

        // Act
        Health health = healthIndicator.health();

        // Assert
        assertThat(health.getStatus().getCode()).isEqualTo("DOWN");
        assertThat(health.getDetails().get("Custom Health Check")).isEqualTo("Service is not healthy");
    }

    @Test
    void TestCheckCustomHealth(){
        // Arrange
        CustomHealthIndicator healthIndicator = new CustomHealthIndicator();

        // Act
        boolean result = healthIndicator.checkCustomHealth();

        // Assert
        assertThat(result).isTrue();
    }
}