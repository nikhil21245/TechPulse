package com.techpulse.user_service.service;

import com.techpulse.user_service.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);

    void updateQuizStats(Long userId, boolean passed, int score);
}