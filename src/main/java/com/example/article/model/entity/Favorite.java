package com.example.article.model.entity;

import com.example.article.model.key.BuyerId;
import com.example.article.model.key.FavoriteId;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite")
@IdClass(FavoriteId.class) // составной ключ
public class Favorite {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "article_id")
    private UUID articleId;
}
