package com.example.article.service.impl;

import com.example.article.exception.EntityNotFoundException;
import com.example.article.model.dto.ArticleDTO;
import com.example.article.model.dto.ArticleMiniDTO;
import com.example.article.model.entity.Article;
import com.example.article.model.request.CreateArticleRequest;
import com.example.article.model.response.StringResponse;
import com.example.article.repository.ArticleRepository;
import com.example.article.service.ArticleService;
import com.example.article.service.grpc.ProfileGrpcClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.article.util.CommonStrings.NOT_FOUND_ARTICLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ProfileGrpcClient profileGrpcClient;


    @Transactional
    @Override
    public StringResponse createArticle(CreateArticleRequest createArticleRequest) {
        Article article = modelMapper.map(createArticleRequest, Article.class);
        article.setDate(new Date());
        articleRepository.save(article);
        return new StringResponse("Статья создана!");
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleDTO getArticle(UUID uuidArticle, String userId) throws AccessDeniedException {
        Article article = articleRepository.findById(uuidArticle)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ARTICLE));
        ArticleDTO articleDTO = new ArticleDTO();

        boolean valid = profileGrpcClient.checkUuidAndUsername(article.getUsername(), userId);
        modelMapper.map(article, articleDTO);
        articleDTO.setOwner(valid);
        return articleDTO;
    }


    @Transactional(readOnly = true)
    @Override
    public List<ArticleMiniDTO> getAllArticle(Integer page, Integer size){
        PageRequest pageable = PageRequest.of(page, size);
        Collection<Article> articles = articleRepository.findAllByOrderByDateDesc(pageable);
        return null;
    }


}
