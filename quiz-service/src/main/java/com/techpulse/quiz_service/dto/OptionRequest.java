package com.techpulse.quiz_service.dto;

import lombok.Data;

@Data
public class OptionRequest {
    private String text;
    private boolean correct;
}