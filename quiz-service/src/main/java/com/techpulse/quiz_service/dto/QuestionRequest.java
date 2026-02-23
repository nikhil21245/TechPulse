package com.techpulse.quiz_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String text;
    private int marks;
    private Long quizId;  // To associate with a quiz
    private List<OptionRequest> options;
}