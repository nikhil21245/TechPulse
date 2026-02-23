package com.techpulse.quiz_service.service;

import com.techpulse.quiz_service.dto.OptionRequest;
import com.techpulse.quiz_service.dto.OptionResponse;
import com.techpulse.quiz_service.entity.Option;
import com.techpulse.quiz_service.entity.Question;
import com.techpulse.quiz_service.repository.OptionRepository;
import com.techpulse.quiz_service.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public OptionResponse createOption(Long questionId, OptionRequest request) {
        log.info("Creating option for question ID: {}", questionId);

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        Option option = Option.builder()
                .text(request.getText())
                .correct(request.isCorrect())
                .question(question)
                .build();

        Option saved = optionRepository.save(option);
        log.info("✅ Option created with ID: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public OptionResponse getOptionById(Long id) {
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found with ID: " + id));
        return mapToResponse(option);
    }

    @Override
    public List<OptionResponse> getOptionsByQuestionId(Long questionId) {
        List<Option> options = optionRepository.findByQuestionId(questionId);
        return options.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public OptionResponse updateOption(Long id, OptionRequest request) {
        log.info("Updating option ID: {}", id);

        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found with ID: " + id));

        option.setText(request.getText());
        option.setCorrect(request.isCorrect());

        Option updated = optionRepository.save(option);
        log.info("✅ Option updated: {}", id);

        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteOption(Long id) {
        log.info("Deleting option ID: {}", id);

        if (!optionRepository.existsById(id)) {
            throw new RuntimeException("Option not found with ID: " + id);
        }

        optionRepository.deleteById(id);
        log.info("✅ Option deleted: {}", id);
    }

    /* ---------- MAPPER ---------- */

    private OptionResponse mapToResponse(Option option) {
        return OptionResponse.builder()
                .optionId(option.getId())
                .text(option.getText())
                .correct(option.isCorrect())
                .build();
    }
}