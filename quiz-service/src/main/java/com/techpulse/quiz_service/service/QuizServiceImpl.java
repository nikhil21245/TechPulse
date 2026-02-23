package com.techpulse.quiz_service.service;

import com.techpulse.quiz_service.dto.OptionResponse;
import com.techpulse.quiz_service.dto.QuestionResponse;
import com.techpulse.quiz_service.dto.QuizResponse;
import com.techpulse.quiz_service.entity.Quiz;
import com.techpulse.quiz_service.repository.QuizRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Override
    public QuizResponse getQuizById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        return mapToResponse(quiz);
    }

    @Override
    public List<QuizResponse> getQuizzesByCategory(String category) {
        return quizRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public QuizResponse createQuiz(Quiz quiz) {
        Quiz saved = quizRepository.save(quiz);
        return mapToResponse(saved);
    }

    /* ---------- MAPPER ---------- */

    private QuizResponse mapToResponse(Quiz quiz) {
        return QuizResponse.builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .category(quiz.getCategory())
                .difficulty(quiz.getDifficulty())
                .timeLimit(quiz.getTimeLimit())
                .questions(
                        quiz.getQuestions().stream()
                                .map(q -> QuestionResponse.builder()
                                        .questionId(q.getId())
                                        .text(q.getText())
                                        .marks(q.getMarks())
                                        .options(
                                                q.getOptions().stream()
                                                        .map(o -> OptionResponse.builder()
                                                                .optionId(o.getId())
                                                                .text(o.getText())
                                                                .build())
                                                        .toList()
                                        )
                                        .build())
                                .toList()
                )
                .build();
    }
}

