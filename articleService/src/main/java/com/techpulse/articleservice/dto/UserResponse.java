package com.techpulse.articleservice.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Set<String> interests;
    private int xpPoints;
    private int quizzesAttempted;
    private int quizzesPassed;
}