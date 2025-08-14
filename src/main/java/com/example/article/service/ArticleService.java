package com.example.article.service;

import com.example.article.model.dto.ArticleDTO;
import com.example.article.model.dto.ArticleMiniDTO;
import com.example.article.model.request.CreateArticleRequest;
import com.example.article.model.response.StringResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface ArticleService {

    public StringResponse createArticle(CreateArticleRequest createArticleRequest);

    public List<ArticleMiniDTO> getAllArticle(Integer page, Integer size);

    public ArticleDTO getArticle(UUID uuidArticle, String userId) throws AccessDeniedException;
}
