package com.example.article.service;

import com.example.article.model.dto.ArticleDTO;
import com.example.article.model.dto.ArticleMiniDTO;
import com.example.article.model.enums.Role;
import com.example.article.model.request.ArticleRequest;
import com.example.article.model.response.StringResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface ArticleService {

    StringResponse createArticle(ArticleRequest articleRequest, String userIdHeader);

    List<ArticleMiniDTO> getAllArticle(Integer page, Integer size);

    ArticleDTO getArticle(UUID uuidArticle, String userIdHeader, String userId);

    StringResponse editArticle(UUID article, ArticleRequest articleRequest, String username);

    StringResponse deleteArticle(UUID articleId, String username, Role role);

    StringResponse purchasingAnArticle(UUID articleId, String username);

    StringResponse addToFavoritesArticle(UUID articleId, String userId);
}
