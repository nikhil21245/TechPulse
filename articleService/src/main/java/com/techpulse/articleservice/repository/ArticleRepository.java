package com.techpulse.articleservice.repository;

import com.techpulse.articleservice.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByCategoryIgnoreCase(String category);

    List<Article> findByAuthorId(Long authorId);

    List<Article> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);

    // trending: top by views
    List<Article> findAllByOrderByViewsDesc(Pageable pageable);
}
