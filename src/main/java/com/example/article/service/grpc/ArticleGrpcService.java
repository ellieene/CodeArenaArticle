package com.example.article.service.grpc;

import com.example.article.model.entity.Article;
import com.example.article.model.entity.Favorite;
import com.example.article.repository.ArticleRepository;
import com.example.article.repository.FavoriteRepository;
import com.example.grpc.profileAndArticle.*;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class ArticleGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final FavoriteRepository favoriteRepository;
    private final ArticleRepository articleRepository;

    @Override
    public void getArticleFromFavoritesByUser(FavoriteRequest request,
                                              StreamObserver<FavoriteResponse> responseObserver) {
        try {
            UUID userId = UUID.fromString(request.getUserId());

            Optional<List<Favorite>> favoritesOpt = favoriteRepository.findAllByUserId(userId);

            FavoriteResponse.Builder responseBuilder = FavoriteResponse.newBuilder();


            if (favoritesOpt.isPresent()) {
                List<Favorite> favorites = favoritesOpt.get();
                for (Favorite fav : favorites) {
                    articleRepository.findById(fav.getArticleId()).ifPresent(article -> {
                        FavoriteArticle favArticle = FavoriteArticle.newBuilder()
                                .setUsername(article.getUsername())
                                .setArticleId(article.getId().toString())
                                .setTitle(article.getTitle())
                                .setPrice(article.getPrice())
                                .build();
                        responseBuilder.addArticles(favArticle);
                    });
                }
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}