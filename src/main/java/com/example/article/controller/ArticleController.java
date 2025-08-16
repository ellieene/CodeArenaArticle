package com.example.article.controller;

import com.example.article.model.dto.ArticleDTO;
import com.example.article.model.enums.Role;
import com.example.article.model.request.ArticleRequest;
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

import java.util.UUID;

@RestController
@RequestMapping("/article")
@Tag(name = "Code Arena Article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleServiceImpl articleService;

    @PostMapping(value = "/create-article")
    @Operation(summary = "Создание Статьи")
    public ResponseEntity<StringResponse> createArticle(
            @RequestBody ArticleRequest articleRequest,
            @RequestHeader(value = "username", required = false) String username) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.createArticle(articleRequest, username));
    }

    @GetMapping(value = "/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение определенной статьи")
    public ResponseEntity<ArticleDTO> getArticle(
            @Parameter(description = "ID статьи", required = true)
            @PathVariable("articleId") UUID articleId,

            @RequestHeader(value = "username", required = false) String username,
            @RequestHeader(value = "userId", required = false) String userId
    ) {
        return ResponseEntity.ok(articleService.getArticle(articleId, username, userId));
    }

//    @GetMapping("/getAll")
//    public ResponseEntity<?> getAllArticle() {
//        return ResponseEntity.ok();
//    }

    @Operation(summary = "Изменение статьи")
    @PutMapping("/edit-article/{articleId}")
    public ResponseEntity<StringResponse> editArticle(@PathVariable UUID articleId,
                                                      @RequestHeader(value = "username", required = false) String username,
                                                      @RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(articleService.editArticle(articleId, articleRequest, username));
    }

    @Operation(summary = "Удаление статьи")
    @DeleteMapping("/delete-article/{articleId}")
    public ResponseEntity<StringResponse> deleteArticle(@PathVariable UUID articleId,
                                                        @RequestHeader(value = "username", required = false) String username,
                                                        @RequestHeader(value = "role", required = false) Role role) {
        return ResponseEntity.ok(articleService.deleteArticle(articleId, username, role));
    }
    //+

    @Operation(summary = "Покупка статьи")
    @PostMapping("/purchasing-an-article/{articleId}")
    public ResponseEntity<StringResponse> purchasingAnArticle(@PathVariable UUID articleId,
                                                              @RequestHeader(value = "username", required = false) String username) {
        return ResponseEntity.ok(articleService.purchasingAnArticle(articleId, username));
    }

// @PostMapping("/add-to-favorites/{articleId}")
//    public ResponseEntity<?> addToFavoritesArticle() {
//        return ResponseEntity.ok();
//    }




}
