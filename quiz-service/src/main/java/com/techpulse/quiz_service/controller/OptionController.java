package com.techpulse.quiz_service.controller;

import com.techpulse.quiz_service.dto.OptionRequest;
import com.techpulse.quiz_service.dto.OptionResponse;
import com.techpulse.quiz_service.service.OptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
@Slf4j
public class OptionController {

    private final OptionService optionService;

    /**
     * Create a new option for a question
     */
    @PostMapping("/question/{questionId}")
    public ResponseEntity<OptionResponse> createOption(
            @PathVariable Long questionId,
            @RequestBody OptionRequest request) {
        log.info("📝 Creating option for question: {}", questionId);
        OptionResponse response = optionService.createOption(questionId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get option by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<OptionResponse> getOptionById(@PathVariable Long id) {
        OptionResponse response = optionService.getOptionById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all options for a question
     */
    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<OptionResponse>> getOptionsByQuestion(@PathVariable Long questionId) {
        List<OptionResponse> options = optionService.getOptionsByQuestionId(questionId);
        return ResponseEntity.ok(options);
    }

    /**
     * Update an option
     */
    @PutMapping("/{id}")
    public ResponseEntity<OptionResponse> updateOption(
            @PathVariable Long id,
            @RequestBody OptionRequest request) {
        log.info("✏️ Updating option: {}", id);
        OptionResponse response = optionService.updateOption(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an option
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long id) {
        log.info("🗑️ Deleting option: {}", id);
        optionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}