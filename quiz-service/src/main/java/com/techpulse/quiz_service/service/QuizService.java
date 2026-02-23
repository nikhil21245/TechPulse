package com.techpulse.quiz_service.service;

import com.techpulse.quiz_service.dto.QuizResponse;
import com.techpulse.quiz_service.entity.Quiz;

import java.util.List;

public interface QuizService {

    QuizResponse getQuizById(Long quizId);

    List<QuizResponse> getQuizzesByCategory(String category);

    QuizResponse createQuiz(Quiz quiz);
}
