package com.techpulse.quiz_service.controller;

import com.techpulse.quiz_service.dto.QuizResultResponse;
import com.techpulse.quiz_service.dto.SubmitQuizRequest;
import com.techpulse.quiz_service.service.QuizAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizAttemptController {

    private final QuizAttemptService quizAttemptService;

    @GetMapping("/test")
    public String test() {
        return "Quiz service working via gateway";
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResultResponse> submit(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody SubmitQuizRequest request
    ) {
        return ResponseEntity.ok(
                quizAttemptService.submitQuiz(userId, request)
        );
    }
}

