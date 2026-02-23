package com.techpulse.articleservice.client;


import com.techpulse.articleservice.config.FeignAuthInterceptor;
import com.techpulse.articleservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "USER-SERVICE",  // same name registered in Eureka
        path = "/internal/users",
        configuration = FeignAuthInterceptor.class
)
public interface UserClient {

    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);
}