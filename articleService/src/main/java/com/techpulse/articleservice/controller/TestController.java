
package com.techpulse.articleservice.controller;

import com.techpulse.articleservice.client.UserClient;
import com.techpulse.articleservice.dto.UserResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final UserClient userClient;

    @GetMapping("/feign/user/{id}")
    public ResponseEntity<?> testFeignCall(@PathVariable Long id) {
        try {
            log.info("🔍 Testing Feign call to User Service for ID: {}", id);

            UserResponse user = userClient.getUserById(id);

            log.info("✅ Feign call successful! Retrieved user: {}", user.getName());

            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("message", "✅ Inter-service communication working!");
            response.put("articleService", "localhost:8080");
            response.put("userService", "localhost:8081");
            response.put("communicationMethod", "Feign Client via Eureka");
            response.put("userData", user);

            return ResponseEntity.ok(response);

        } catch (FeignException.NotFound e) {
            log.error("❌ User not found: {}", id);
            return ResponseEntity.status(404).body(Map.of(
                    "status", "ERROR",
                    "message", "User not found with ID: " + id
            ));

        } catch (FeignException e) {
            log.error("❌ Feign communication error: {}", e.getMessage());
            return ResponseEntity.status(503).body(Map.of(
                    "status", "ERROR",
                    "message", "Cannot communicate with User Service",
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/feign/health")
    public ResponseEntity<?> feignHealthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("articleService", "UP");

        try {
            userClient.getUserById(1L);
            health.put("userServiceConnection", "UP ✅");
            health.put("feignClient", "WORKING ✅");
            return ResponseEntity.ok(health);
        } catch (Exception e) {
            health.put("userServiceConnection", "DOWN ❌");
            health.put("feignClient", "ERROR");
            health.put("error", e.getMessage());
            return ResponseEntity.status(503).body(health);
        }
    }
}