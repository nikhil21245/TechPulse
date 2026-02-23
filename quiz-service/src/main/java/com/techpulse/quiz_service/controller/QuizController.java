package com.techpulse.quiz_service.controller;

import com.techpulse.quiz_service.dto.QuizResponse;
import com.techpulse.quiz_service.entity.Quiz;
import com.techpulse.quiz_service.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
@Slf4j
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @GetMapping
    public ResponseEntity<List<QuizResponse>> byCategory(@RequestParam String category) {
        return ResponseEntity.ok(quizService.getQuizzesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<QuizResponse> createQuiz(@RequestBody Quiz quiz) {
        try {
            log.info("📝 Received request to create quiz: {}", quiz.getTitle());

            QuizResponse response = quizService.createQuiz(quiz);

            log.info("✅ Quiz created successfully with ID: {}", response.getQuizId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("❌ Error creating quiz: {}", e.getMessage(), e);
            throw e;
        }
    }
}