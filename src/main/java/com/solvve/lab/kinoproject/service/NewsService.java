package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.News;
import com.solvve.lab.kinoproject.dto.news.NewsCreateDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPatchDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPutDTO;
import com.solvve.lab.kinoproject.dto.news.NewsReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TranslationService translationService;

    private News getNewsRequired(UUID id) {
        return newsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(News.class, id));
    }

    public NewsReadDTO getNews(UUID id) {
        News news = getNewsRequired(id);
        return translationService.translate(news, NewsReadDTO.class);

    }


    public NewsReadDTO createNews(NewsCreateDTO create) {
        News news = translationService.translate(create, News.class);
        news = newsRepository.save(news);
        return translationService.translate(news, NewsReadDTO.class);
    }

    public NewsReadDTO patchNews(UUID id, NewsPatchDTO patch) {
        News news = getNewsRequired(id);
        translationService.patchEntityNews(patch, news);
        news = newsRepository.save(news);
        return translationService.translate(news, NewsReadDTO.class);
    }

    public NewsReadDTO updateNews(UUID id, NewsPutDTO put) {
        News news = getNewsRequired(id);
        translationService.updateEntityNews(put, news);
        news = newsRepository.save(news);
        return translationService.translate(news, NewsReadDTO.class);
    }


    public void deleteNews(UUID id) {
        newsRepository.delete(getNewsRequired(id));
    }

}
