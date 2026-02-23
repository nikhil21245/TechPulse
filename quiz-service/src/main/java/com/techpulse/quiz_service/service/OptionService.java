package com.techpulse.quiz_service.service;

import com.techpulse.quiz_service.dto.OptionRequest;
import com.techpulse.quiz_service.dto.OptionResponse;

import java.util.List;

public interface OptionService {
    OptionResponse createOption(Long questionId, OptionRequest request);
    OptionResponse getOptionById(Long id);
    List<OptionResponse> getOptionsByQuestionId(Long questionId);
    OptionResponse updateOption(Long id, OptionRequest request);
    void deleteOption(Long id);
}