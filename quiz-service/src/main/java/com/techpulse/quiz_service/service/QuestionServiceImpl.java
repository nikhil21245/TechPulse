package com.techpulse.quiz_service.service;

import com.techpulse.quiz_service.dto.OptionRequest;
import com.techpulse.quiz_service.dto.OptionResponse;
import com.techpulse.quiz_service.dto.QuestionRequest;
import com.techpulse.quiz_service.dto.QuestionResponse;
import com.techpulse.quiz_service.entity.Option;
import com.techpulse.quiz_service.entity.Question;
import com.techpulse.quiz_service.entity.Quiz;
import com.techpulse.quiz_service.repository.QuestionRepository;
import com.techpulse.quiz_service.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    @Override
    @Transactional
    public QuestionResponse createQuestion(QuestionRequest request) {
        log.info("Creating question for quiz ID: {}", request.getQuizId());

        // Fetch the quiz
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with ID: " + request.getQuizId()));

        // Create question
        Question question = Question.builder()
                .text(request.getText())
                .marks(request.getMarks())
                .quiz(quiz)
                .options(new ArrayList<>())
                .build();

        // Create and associate options
        if (request.getOptions() != null) {
            for (OptionRequest optionReq : request.getOptions()) {
                Option option = Option.builder()
                        .text(optionReq.getText())
                        .correct(optionReq.isCorrect())
                        .question(question)
                        .build();
                question.getOptions().add(option);
            }
        }

        Question saved = questionRepository.save(question);
        log.info("✅ Question created with ID: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));
        return mapToResponse(question);
    }

    @Override
    public List<QuestionResponse> getQuestionsByQuizId(Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        return questions.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public QuestionResponse updateQuestion(Long id, QuestionRequest request) {
        log.info("Updating question ID: {}", id);

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));

        // Update question fields
        question.setText(request.getText());
        question.setMarks(request.getMarks());

        // Update options
        if (request.getOptions() != null) {
            // Clear existing options
            question.getOptions().clear();

            // Add new options
            for (OptionRequest optionReq : request.getOptions()) {
                Option option = Option.builder()
                        .text(optionReq.getText())
                        .correct(optionReq.isCorrect())
                        .question(question)
                        .build();
                question.getOptions().add(option);
            }
        }

        Question updated = questionRepository.save(question);
        log.info("✅ Question updated: {}", id);

        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        log.info("Deleting question ID: {}", id);

        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found with ID: " + id);
        }

        questionRepository.deleteById(id);
        log.info("✅ Question deleted: {}", id);
    }

    /* ---------- MAPPER ---------- */

    private QuestionResponse mapToResponse(Question question) {
        return QuestionResponse.builder()
                .questionId(question.getId())
                .text(question.getText())
                .marks(question.getMarks())
                .quizId(question.getQuiz().getId())
                .options(
                        question.getOptions().stream()
                                .map(o -> OptionResponse.builder()
                                        .optionId(o.getId())
                                        .text(o.getText())
                                        .correct(o.isCorrect())
                                        .build())
                                .toList()
                )
                .build();
    }
}