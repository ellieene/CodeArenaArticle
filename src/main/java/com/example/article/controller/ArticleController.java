package com.example.article.controller;

import com.example.article.model.dto.ArticleDTO;
import com.example.article.model.entity.Article;
import com.example.article.model.request.CreateArticleRequest;
import com.example.article.model.response.StringResponse;
import com.example.article.service.impl.ArticleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequestMapping("/article")
@Tag(name = "Code Arena Article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleServiceImpl articleService;

    @PostMapping(value = "/create-article")
    @Operation(summary = "Создание Статьи")
    public ResponseEntity<StringResponse> createArticle(@RequestBody CreateArticleRequest createArticleRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.createArticle(createArticleRequest));
    }

    @GetMapping(value = "/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение определенной статьи")
    public ResponseEntity<ArticleDTO> getArticle(
            @Parameter(description = "ID статьи", required = true)
            @PathVariable("articleId") UUID articleId,

            @Parameter(description = "ID пользователя (опционально)")
            @RequestHeader(value = "userId", required = false)
            String userId) throws AccessDeniedException {
        return ResponseEntity.ok(articleService.getArticle(articleId, userId));
    }

//    @GetMapping("/getAll")
//    public ResponseEntity<?> getAllArticle() {
//        return ResponseEntity.ok();
//    }

//    @PutMapping("/edit-article/{articleId}")
//    public ResponseEntity<?> editArticle() {
//        return ResponseEntity.ok();
//    }

//    @DeleteMapping("/delete-article/{articleId}")
//    public ResponseEntity<?> deleteArticle() {
//        return ResponseEntity.ok();
//    }

// @PostMapping("/purchasing-an-article/{articleId}")
//    public ResponseEntity<?> purchasingAnArticle() {
//        return ResponseEntity.ok();
//    }

// @PostMapping("/add-to-favorites/{articleId}")
//    public ResponseEntity<?> addToFavoritesArticle() {
//        return ResponseEntity.ok();
//    }




}
