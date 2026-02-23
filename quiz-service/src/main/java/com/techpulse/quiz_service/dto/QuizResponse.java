package com.techpulse.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {

    private Long quizId;
    private String title;
    private String category;
    private String difficulty;
    private Integer timeLimit;

    private List<QuestionResponse> questions;
}
