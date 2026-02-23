package com.techpulse.user_service.controller;

import com.techpulse.user_service.dto.UserResponse;
import com.techpulse.user_service.entity.User;
import com.techpulse.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
@Slf4j
public class InternalUserController {

    private final UserService userService;

    /**
     * Get user by ID (for inter-service calls)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserByIdInternal(@PathVariable Long id) {
        log.info("Internal service call: Getting user {}", id);
        User user = userService.getUserById(id);
        UserResponse response = mapToResponse(user);
        return ResponseEntity.ok(response);
    }

    /**
     * ✅ Update quiz statistics (called by Quiz Service)
     */
    @PostMapping("/{id}/quiz-result")
    public ResponseEntity<Void> updateQuizStats(
            @PathVariable Long id,
            @RequestParam boolean passed,
            @RequestParam int score
    ) {
        log.info("📊 Internal service call: Updating quiz stats for user {} - Passed: {}, Score: {}",
                id, passed, score);

        try {
            userService.updateQuizStats(id, passed, score);
            log.info("✅ Quiz stats updated successfully for user {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("❌ Failed to update quiz stats: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Convert User entity to UserResponse DTO
     */
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .interests(user.getInterests())
                .xpPoints(user.getXpPoints())
                .quizzesAttempted(user.getQuizzesAttempted())
                .quizzesPassed(user.getQuizzesPassed())
                .build();
    }
}