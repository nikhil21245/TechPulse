package com.techpulse.articleservice.controller;

import com.techpulse.articleservice.dto.ArticleRequest;
import com.techpulse.articleservice.dto.ArticleResponse;
import com.techpulse.articleservice.service.Articleservice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final Articleservice service;

    // PUBLIC
    @GetMapping
    public ResponseEntity<List<ArticleResponse>> all() {
        return ResponseEntity.ok(service.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> get(@PathVariable Long id) {
        // increment views (optional). Many apps increment on the client after fetch;
        ArticleResponse r = service.incrementViews(id);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ArticleResponse>> byCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.getArticlesByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticleResponse>> search(@RequestParam("q") String q) {
        return ResponseEntity.ok(service.searchArticles(q));
    }

    @GetMapping("/trending")
    public ResponseEntity<List<ArticleResponse>> trending(@RequestParam(value = "limit", defaultValue = "5") int limit) {
        return ResponseEntity.ok(service.getTrendingArticles(limit));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<ArticleResponse>> byAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(service.getArticlesByAuthor(authorId));
    }

    // PROTECTED - requires X-User-Id header (API Gateway should set after auth)
    @PostMapping
    public ResponseEntity<ArticleResponse> create(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody ArticleRequest req) {
        return ResponseEntity.ok(service.createArticle(userId, req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> update(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest req) {
        return ResponseEntity.ok(service.updateArticle(userId, id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        service.deleteArticle(userId, id);
        return ResponseEntity.noContent().build();
    }
}
