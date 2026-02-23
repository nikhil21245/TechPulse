package com.techpulse.user_service.dto;

import com.techpulse.user_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Set<String> interests;
    private int xpPoints;
    private int quizzesAttempted;
    private int quizzesPassed;
}
