package com.example.article.repository;

import com.example.article.model.entity.Favorite;
import com.example.article.model.key.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    Optional<List<Favorite>> findAllByUserId(UUID userId);
    boolean existsByUserIdAndArticleId(UUID userId, UUID articleId);
}
