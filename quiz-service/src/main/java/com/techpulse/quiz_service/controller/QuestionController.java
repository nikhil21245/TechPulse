package com.techpulse.quiz_service.controller;

import com.techpulse.quiz_service.dto.QuestionRequest;
import com.techpulse.quiz_service.dto.QuestionResponse;
import com.techpulse.quiz_service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    /**
     * Create a new question
     */
    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequest request) {
        log.info("📝 Creating question for quiz: {}", request.getQuizId());
        QuestionResponse response = questionService.createQuestion(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get question by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        QuestionResponse response = questionService.getQuestionById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all questions for a quiz
     */
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionResponse>> getQuestionsByQuiz(@PathVariable Long quizId) {
        List<QuestionResponse> questions = questionService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }

    /**
     * Update a question
     */
    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable Long id,
            @RequestBody QuestionRequest request) {
        log.info("✏️ Updating question: {}", id);
        QuestionResponse response = questionService.updateQuestion(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a question
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        log.info("🗑️ Deleting question: {}", id);
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}