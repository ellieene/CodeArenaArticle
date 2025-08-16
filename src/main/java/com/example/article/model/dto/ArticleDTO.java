package com.example.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    private String username;
    private String article;
    private String title;
    private Date date;
    private int price;
    private boolean purchased;
    private boolean owner;
}
