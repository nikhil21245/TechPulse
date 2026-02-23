package com.techpulse.articleservice.service.impl;

import com.techpulse.articleservice.dto.ArticleRequest;
import com.techpulse.articleservice.dto.ArticleResponse;
import com.techpulse.articleservice.entity.Article;
import com.techpulse.articleservice.exception.ArticleNotFoundException;
import com.techpulse.articleservice.repository.ArticleRepository;
import com.techpulse.articleservice.service.Articleservice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements Articleservice {

    private final ArticleRepository repo;

    private Set<String> toSet(String csv) {
        if (csv == null || csv.isBlank()) return Collections.emptySet();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private String fromSet(Set<String> set) {
        if (set == null || set.isEmpty()) return null;
        return set.stream().map(String::trim).collect(Collectors.joining(","));
    }

    private ArticleResponse toResponse(Article a) {
        return ArticleResponse.builder()
                .id(a.getId())
                .authorId(a.getAuthorId())
                .title(a.getTitle())
                .content(a.getContent())
                .category(a.getCategory())
                .tags(toSet(a.getTags()))
                .views(a.getViews())
                .createdAt(a.getCreatedAt())
                .updatedAt(a.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public ArticleResponse createArticle(Long authorId, ArticleRequest req) {
        Article a = Article.builder()
                .authorId(authorId)
                .title(req.getTitle())
                .content(req.getContent())
                .category(req.getCategory())
                .tags(fromSet(req.getTags()))
                .views(0)
                .build();
        Article saved = repo.save(a);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ArticleResponse updateArticle(Long authorId, Long articleId, ArticleRequest req) {
        Article a = repo.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if (!Objects.equals(a.getAuthorId(), authorId)) {
            throw new RuntimeException("Not authorized to update this article");
        }
        a.setTitle(req.getTitle());
        a.setContent(req.getContent());
        a.setCategory(req.getCategory());
        a.setTags(fromSet(req.getTags()));
        Article saved = repo.save(a);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteArticle(Long authorId, Long articleId) {
        Article a = repo.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if (!Objects.equals(a.getAuthorId(), authorId)) {
            throw new RuntimeException("Not authorized to delete this article");
        }
        repo.delete(a);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(Long id) {
        Article a = repo.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        return toResponse(a);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> getAllArticles() {
        return repo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticlesByCategory(String category) {
        return repo.findByCategoryIgnoreCase(category).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> searchArticles(String query) {
        return repo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> getTrendingArticles(int limit) {
        return repo.findAllByOrderByViewsDesc(PageRequest.of(0, Math.max(limit, 5)))
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> getArticlesByAuthor(Long authorId) {
        return repo.findByAuthorId(authorId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ArticleResponse incrementViews(Long id) {
        Article a = repo.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
        a.setViews(a.getViews() + 1);
        Article saved = repo.save(a);
        return toResponse(saved);
    }
}
