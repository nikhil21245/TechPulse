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
public class QuestionResponse {

    private Long questionId;
    private String text;
    private Integer marks;
    private Long quizId;
    private List<OptionResponse> options;
}

