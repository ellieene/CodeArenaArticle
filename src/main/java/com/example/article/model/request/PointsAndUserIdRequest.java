package com.example.article.model.request;

import java.util.UUID;

public record PointsAndUserIdRequest(
        int points,
        UUID userId
) {
}
