package com.techpulse.articleservice.service;

import com.techpulse.articleservice.dto.ArticleRequest;
import com.techpulse.articleservice.dto.ArticleResponse;

import java.util.List;

public interface Articleservice {
    ArticleResponse createArticle(Long authorId, ArticleRequest req);
    ArticleResponse updateArticle(Long authorId, Long articleId, ArticleRequest req);
    void deleteArticle(Long authorId, Long articleId);
    ArticleResponse getArticleById(Long id);
    List<ArticleResponse> getAllArticles();
    List<ArticleResponse> getArticlesByCategory(String category);
    List<ArticleResponse> searchArticles(String query);
    List<ArticleResponse> getTrendingArticles(int limit);
    List<ArticleResponse> getArticlesByAuthor(Long authorId);
    ArticleResponse incrementViews(Long id);
}
