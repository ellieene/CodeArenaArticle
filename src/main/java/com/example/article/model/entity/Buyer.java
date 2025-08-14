package com.example.article.model.entity;

import com.example.article.model.key.BuyerId;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buyer")
@IdClass(BuyerId.class) // составной ключ
public class Buyer {
    @Id
    @Column(name = "buyer_id")
    private UUID buyerId;

    @Id
    @Column(name = "article_id")
    private UUID articleId;
}