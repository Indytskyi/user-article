package com.indytskyi.userservice.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.indytskyi.userservice.dto.request.ArticleRequestDto;
import com.indytskyi.userservice.dto.response.ArticleResponseDto;
import com.indytskyi.userservice.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(
            @RequestBody @Valid ArticleRequestDto articleRequestDto,
            @RequestHeader("Authorization") String bearerToken) {

        return ResponseEntity
                .status(CREATED)
                .body(articleService.saveArticle(articleRequestDto, bearerToken));
    }
}
