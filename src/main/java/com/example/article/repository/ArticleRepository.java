package com.example.article.repository;

import com.example.article.model.entity.Article;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    Collection<Article> findAllByOrderByDateDesc(PageRequest pageRequest);
}
