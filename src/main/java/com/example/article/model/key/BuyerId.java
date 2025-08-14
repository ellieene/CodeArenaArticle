package com.example.article.model.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerId implements Serializable {
    private UUID buyerId;
    private UUID articleId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyerId buyerId1 = (BuyerId) o;
        return Objects.equals(buyerId, buyerId1.buyerId) &&
                Objects.equals(articleId, buyerId1.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyerId, articleId);
    }
}