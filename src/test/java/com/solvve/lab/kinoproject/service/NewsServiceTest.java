package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.News;
import com.solvve.lab.kinoproject.dto.news.NewsCreateDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPatchDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPutDTO;
import com.solvve.lab.kinoproject.dto.news.NewsReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.NewsRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class NewsServiceTest extends BaseTest {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    private News createNews() {
        News news = generateFlatEntityWithoutId(News.class);
        return newsRepository.save(news);
    }

    @Test
    public void testGetNews() {
        News news = createNews();

        NewsReadDTO newsReadDTO = newsService.getNews(news.getId());
        Assertions.assertThat(newsReadDTO)
                .isEqualToIgnoringGivenFields(news, "createdAt", "updatedAt");

    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetNewsWrongId() {
        newsService.getNews(UUID.randomUUID());

    }

    @Test
    public void testCreateNews() {
        NewsCreateDTO create = generateObject(NewsCreateDTO.class);
        NewsReadDTO read = newsService.createNews(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        News news = newsRepository.findById(read.getId()).get();
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(news, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchNews() {
        News news = createNews();

        NewsPatchDTO patch = generateObject(NewsPatchDTO.class);
        NewsReadDTO read = newsService.patchNews(news.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        news = newsRepository.findById(read.getId()).get();
        Assertions.assertThat(news)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPutNews() {
        News news = createNews();

        NewsPutDTO put = generateObject(NewsPutDTO.class);
        NewsReadDTO read = newsService.updateNews(news.getId(), put);

        Assertions.assertThat(put)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");

        news = newsRepository.findById(read.getId()).get();
        Assertions.assertThat(news)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchNewsEmptyPatch() {
        News news = createNews();

        NewsPatchDTO patch = new NewsPatchDTO();

        NewsReadDTO read = newsService.patchNews(news.getId(), patch);

        Assert.assertNotNull(read.getTextNews());

        News newsAfterUpdate = newsRepository.findById(read.getId()).get();

        Assert.assertNotNull(newsAfterUpdate.getTextNews());

        Assertions.assertThat(news)
                .isEqualToIgnoringGivenFields(newsAfterUpdate,
                        "createdAt", "updatedAt");
    }

    @Test
    public void testDeleteNews() {
        News news = createNews();

        newsService.deleteNews(news.getId());

        Assert.assertFalse(newsRepository.existsById(news.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNewsNotFoundId() {
        newsService.deleteNews(UUID.randomUUID());
    }

}