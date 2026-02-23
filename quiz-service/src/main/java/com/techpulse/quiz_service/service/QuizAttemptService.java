package com.techpulse.quiz_service.service;

import com.techpulse.quiz_service.dto.QuizResultResponse;
import com.techpulse.quiz_service.dto.SubmitQuizRequest;

public interface QuizAttemptService {

    QuizResultResponse submitQuiz(
            Long userId,
            SubmitQuizRequest request
    );
}
