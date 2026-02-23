package com.techpulse.user_service.controller;

import com.techpulse.user_service.dto.UserRegisterRequest;
import com.techpulse.user_service.dto.UserResponse;
import com.techpulse.user_service.entity.User;
import com.techpulse.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  // ✅ ADD THIS
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j  // ✅ ADD THIS
public class UserController {

    private final UserService userService;

    // ✅ Register new user
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .interests(request.getInterests())
                .build();

        User saved = userService.registerUser(user);

        UserResponse response = UserResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .role(saved.getRole())
                .interests(saved.getInterests())
                .xpPoints(saved.getXpPoints())
                .quizzesAttempted(saved.getQuizzesAttempted())
                .quizzesPassed(saved.getQuizzesPassed())
                .build();

        return ResponseEntity.ok(response);
    }

    // ✅ Get all users
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .interests(user.getInterests())
                        .xpPoints(user.getXpPoints())
                        .quizzesAttempted(user.getQuizzesAttempted())
                        .quizzesPassed(user.getQuizzesPassed())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    // ✅ Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .interests(user.getInterests())
                .xpPoints(user.getXpPoints())
                .quizzesAttempted(user.getQuizzesAttempted())
                .quizzesPassed(user.getQuizzesPassed())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/login/google")
    public void googleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }
}

// tested the jwt got the token them tesdted all users by get got all the users ...is it fine and enough for user service for thi projrct ???or we can improve me or just enough for this one ???