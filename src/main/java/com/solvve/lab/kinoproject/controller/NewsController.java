package com.solvve.lab.kinoproject.controller;


import com.solvve.lab.kinoproject.dto.news.NewsCreateDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPatchDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPutDTO;
import com.solvve.lab.kinoproject.dto.news.NewsReadDTO;
import com.solvve.lab.kinoproject.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/{id}")
    public NewsReadDTO getNews(@PathVariable UUID id) {
        return newsService.getNews(id);
    }

    @PostMapping
    public NewsReadDTO createNews(@RequestBody NewsCreateDTO create) {
        return newsService.createNews(create);
    }

    @PatchMapping("/{id}")
    public NewsReadDTO patchNews(@PathVariable UUID id, @RequestBody NewsPatchDTO patch) {
        return newsService.patchNews(id, patch);
    }

    @PutMapping("/{id}")
    public NewsReadDTO updateNews(@PathVariable UUID id, @RequestBody NewsPutDTO put) {
        return newsService.updateNews(id, put);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNews(@PathVariable UUID id) {
        newsService.deleteNews(id);
    }
}
