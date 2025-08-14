package com.example.article.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


public record CreateArticleRequest(
        @Schema(description = "Username", example = "user1")
        @Size(min = 5, max = 50, message = "Username должен содержать от 5 до 50 символов.")
        @NotBlank(message = "Username не может быть пустым.")
        String username,

        @Schema(description = "Article", example = "Статья")
        @NotBlank(message = "Статья не может быть пустой.")
        String article,

        @Schema(description = "Description Article", example = "Описание статьи")
        @Size(min = 50, max = 256, message = "Описание статьи должно содержать от 5 до 256 символов.")
        @NotBlank(message = "Описание не может быть пустым.")
        String description_article,

        @Schema(description = "Price", example = "0")
        @Min(value = 0, message = "Цена не может быть отрицательной")
        @Max(value = 500, message = "Цена не может превышать 500 монет")
        int price
) {}
