package com.techpulse.quiz_service.service;

import com.techpulse.quiz_service.dto.QuestionRequest;
import com.techpulse.quiz_service.dto.QuestionResponse;

import java.util.List;

public interface QuestionService {
    QuestionResponse createQuestion(QuestionRequest request);
    QuestionResponse getQuestionById(Long id);
    List<QuestionResponse> getQuestionsByQuizId(Long quizId);
    QuestionResponse updateQuestion(Long id, QuestionRequest request);
    void deleteQuestion(Long id);
}