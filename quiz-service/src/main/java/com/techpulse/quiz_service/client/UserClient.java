package com.techpulse.quiz_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "USER-SERVICE",  // ✅ Uppercase to match Eureka
        path = "/internal/users"  // ✅ Use internal endpoint to bypass JWT
)
public interface UserClient {

    @PostMapping("/{id}/quiz-result")
    void updateQuizStats(
            @PathVariable Long id,
            @RequestParam boolean passed,
            @RequestParam int score
    );
}