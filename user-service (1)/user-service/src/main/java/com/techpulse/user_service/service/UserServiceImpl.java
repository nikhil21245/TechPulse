package com.techpulse.user_service.service;
import com.techpulse.user_service.entity.Role;
import com.techpulse.user_service.entity.User;
import com.techpulse.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  // ✅ ADD THIS
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j  // ✅ ADD THIS
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        // check if user already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered: " + user.getEmail());
        }

        // default role
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(null); // for OAuth users
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    @Transactional
    public void updateQuizStats(Long userId, boolean passed, int score) {
        User user = getUserById(userId);

        // Increment quiz attempts
        user.setQuizzesAttempted(user.getQuizzesAttempted() + 1);

        // Increment passed count if quiz was passed
        if (passed) {
            user.setQuizzesPassed(user.getQuizzesPassed() + 1);
        }

        // Add XP points based on score
        int xpGained = score * 10; // 10 XP per point
        user.setXpPoints(user.getXpPoints() + xpGained);

        userRepository.save(user);

        log.info("✅ Updated quiz stats for user {}: Attempted={}, Passed={}, XP=+{}",
                userId, user.getQuizzesAttempted(), user.getQuizzesPassed(), xpGained);
    }
}