package com.example.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.article.repository")
public class CodeArenaArticle {

	public static void main(String[] args) {
		SpringApplication.run(CodeArenaArticle.class, args);
	}

}
