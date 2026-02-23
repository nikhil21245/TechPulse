package com.techpulse.quiz_service.service;

import com.techpulse.quiz_service.client.UserClient;
import com.techpulse.quiz_service.dto.AnswerRequest;
import com.techpulse.quiz_service.dto.QuizResultResponse;
import com.techpulse.quiz_service.dto.SubmitQuizRequest;
import com.techpulse.quiz_service.entity.Option;
import com.techpulse.quiz_service.entity.Quiz;
import com.techpulse.quiz_service.entity.QuizAttempt;
import com.techpulse.quiz_service.repository.QuizAttemptRepository;
import com.techpulse.quiz_service.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuizAttemptServiceImpl implements QuizAttemptService {

    private final QuizRepository quizRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final UserClient userClient;


    @Override
    public QuizResultResponse submitQuiz(Long userId, SubmitQuizRequest request) {

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        int score = 0;
        int correctCount = 0;

        Map<Long, Option> correctOptions = quiz.getQuestions().stream()
                .flatMap(q -> q.getOptions().stream())
                .filter(Option::isCorrect)
                .collect(Collectors.toMap(
                        o -> o.getQuestion().getId(),
                        o -> o
                ));

        for (AnswerRequest answer : request.getAnswers()) {
            Option correct = correctOptions.get(answer.getQuestionId());
            if (correct != null && correct.getId().equals(answer.getSelectedOptionId())) {
                correctCount++;
                score += correct.getQuestion().getMarks();
            }
        }

        boolean passed = score >= quiz.getPassingScore();

        QuizAttempt attempt = QuizAttempt.builder()
                .userId(userId)
                .quiz(quiz)
                .score(score)
                .passed(passed)
                .submittedAt(LocalDateTime.now())
                .build();

        quizAttemptRepository.save(attempt);

        // save stats of user by interservice communication using openfeign

        log.info("✅ Quiz attempt saved for user {}", userId);

        // ✅ Update user stats via Feign with error handling
        try {
            log.info("📞 Calling User Service to update stats for user {}", userId);
            userClient.updateQuizStats(userId, passed, score);
            log.info("✅ User stats updated successfully");
        } catch (feign.FeignException e) {
            log.error("❌ Failed to update user stats via Feign: Status={}, Message={}",
                    e.status(), e.getMessage());
            // Don't fail the entire operation if stats update fails
            // Quiz attempt is already saved
        } catch (Exception e) {
            log.error("❌ Unexpected error calling User Service: {}", e.getMessage(), e);
        }


        return QuizResultResponse.builder()
                .quizId(quiz.getId())
                .userId(userId)
                .totalQuestions(quiz.getQuestions().size())
                .correctAnswers(correctCount)
                .score(score)
                .passed(passed)
                .submittedAt(attempt.getSubmittedAt())
                .build();
    }
}

