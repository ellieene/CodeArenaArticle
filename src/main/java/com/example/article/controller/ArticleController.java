package com.example.article.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Tag(name = "Статьи")
public class ArticleController {

    @PostMapping("/create-article")
    public ResponseEntity<?> createArticle(){
        return ResponseEntity.status(HttpStatus.CREATED).body("Статья отправлена на проверку");
    }

//    @GetMapping("/{articleId}")
//    public ResponseEntity<?> getArticle() {
//        return ResponseEntity.ok();
//    }

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
