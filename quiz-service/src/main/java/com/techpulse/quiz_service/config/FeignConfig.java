package com.techpulse.quiz_service.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.techpulse.quiz_service.client")
public class FeignConfig {
}

