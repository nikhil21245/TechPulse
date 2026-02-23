package com.techpulse.articleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private String category;
    private Set<String> tags;
    private int views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
