package com.example.article.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ArticleMiniDTO {

    private UUID article_id;

    private UserDTO userDTO;

    private String description_article;

    private Date date;

    private int price;
}
