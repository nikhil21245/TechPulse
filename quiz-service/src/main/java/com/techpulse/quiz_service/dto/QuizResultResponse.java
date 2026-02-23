package com.techpulse.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultResponse {

    private Long quizId;
    private Long userId;

    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer score;
    private Boolean passed;

    private LocalDateTime submittedAt;
}
