package com.example.article.repository;

import com.example.article.model.entity.Buyer;
import com.example.article.model.key.BuyerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, UUID> {
    boolean existsByBuyerIdAndArticleId(UUID buyerId, UUID articleId);

}
