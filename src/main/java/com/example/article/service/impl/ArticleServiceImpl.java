package com.example.article.service.impl;

import com.example.article.exception.AccessDeniedException;
import com.example.article.exception.EntityNotFoundException;
import com.example.article.exception.InsufficientFundsException;
import com.example.article.model.dto.ArticleDTO;
import com.example.article.model.dto.ArticleMiniDTO;
import com.example.article.model.entity.Article;
import com.example.article.model.entity.Buyer;
import com.example.article.model.entity.Favorite;
import com.example.article.model.enums.Role;
import com.example.article.model.key.FavoriteId;
import com.example.article.model.request.ArticleRequest;
import com.example.article.model.request.PointsAndUserIdRequest;
import com.example.article.model.response.StringResponse;
import com.example.article.repository.ArticleRepository;
import com.example.article.repository.BuyerRepository;
import com.example.article.repository.FavoriteRepository;
import com.example.article.service.ArticleService;
import com.example.article.client.grpc.ProfileGrpcClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.article.util.CommonStrings.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ProfileGrpcClient profileGrpcClient;
    private final BuyerRepository buyerRepository;
    private final FavoriteRepository favoriteRepository;


    @Transactional
    @Override
    public StringResponse createArticle(ArticleRequest articleRequest, String username) {
        Article article = modelMapper.map(articleRequest, Article.class);
        article.setDate(new Date());
        article.setUsername(username);
        articleRepository.save(article);
        return new StringResponse(ARTICLE_CREATE_SUCCESS);
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleDTO getArticle(UUID articleId, String username, String userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ARTICLE));
        ArticleDTO articleDTO = new ArticleDTO();
        UUID byuerId = UUID.fromString(userId);
        articleDTO.setPurchased(buyerRepository.existsByBuyerIdAndArticleId(byuerId, articleId));
        modelMapper.map(article, articleDTO);
        if (username.equals(article.getUsername())) {
            articleDTO.setOwner(true);
            articleDTO.setPurchased(true);

        }
        return articleDTO;
    }

    @Transactional
    @Override
    public StringResponse editArticle(UUID articleId, ArticleRequest articleRequest, String username) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ARTICLE));

        if (!username.equals(article.getUsername())) {
            log.info("User '{}' does not have edit rights", username);
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        modelMapper.map(articleRequest, article);
        articleRepository.save(article);
        return new StringResponse(ARTICLE_EDIT_SUCCESS);
    }

    @Transactional
    @Override
    public StringResponse deleteArticle(UUID articleId, String username, Role role) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ARTICLE));

        if (!article.getUsername().equals(username) && !role.equals(Role.ADMIN)) {
            log.info("User '{}' does not have delete rights", username);
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        articleRepository.delete(article);
        return new StringResponse(ARTICLE_DELETE_SUCCESS);
    }

    @Transactional
    @Override
    public StringResponse purchasingAnArticle(UUID articleId, String username) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ARTICLE));

        if (article.getUsername().equals(username)) {
            throw new AccessDeniedException(OWNER_CANNOT_BUY_OWN_ARTICLE);
        }

        PointsAndUserIdRequest pointsAndUserId = profileGrpcClient.getPointsAndUserId(username);
        UUID buyerId = pointsAndUserId.userId();
        boolean alreadyPurchased = buyerRepository.existsByBuyerIdAndArticleId(buyerId, articleId);
        if (pointsAndUserId.points() < article.getPrice()) {
            throw new InsufficientFundsException(INSUFFICIENT_FUNDS);
        } else if (alreadyPurchased) {
            throw new AccessDeniedException(THE_ARTICLE_HAS_ALREADY_BEEN_PURCHASED);
        }
        Buyer buyer = Buyer
                .builder()
                .articleId(articleId)
                .buyerId(pointsAndUserId.userId())
                .build();

        buyerRepository.save(buyer);
        int points = pointsAndUserId.points() - article.getPrice();


        return profileGrpcClient.purchasingAnArticle(username, points);
    }

    @Transactional
    @Override
    public StringResponse addToFavoritesArticle(UUID articleId, String userId){


        boolean alreadyPurchased = favoriteRepository.existsByUserIdAndArticleId(UUID.fromString(userId), articleId);
        if (alreadyPurchased) {
            throw new AccessDeniedException(THE_ARTICLE_IS_ALREADY_IN_FAVORITES);
        }
        Favorite favorite = Favorite
                .builder()
                .userId(UUID.fromString(userId))
                .articleId(articleId)
                .build();
        favoriteRepository.save(favorite);
        return new StringResponse("Статья успешно добавлено в 'Избраное'");
    }



    @Transactional(readOnly = true)
    @Override
    public List<ArticleMiniDTO> getAllArticle(Integer page, Integer size){
        PageRequest pageable = PageRequest.of(page, size);
        Collection<Article> articles = articleRepository.findAllByOrderByDateDesc(pageable);
        return null;
    }


}
