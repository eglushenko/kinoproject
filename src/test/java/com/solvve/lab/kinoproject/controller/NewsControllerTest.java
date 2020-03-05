package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvve.lab.kinoproject.domain.News;
import com.solvve.lab.kinoproject.dto.news.NewsCreateDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPatchDTO;
import com.solvve.lab.kinoproject.dto.news.NewsPutDTO;
import com.solvve.lab.kinoproject.dto.news.NewsReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.NewsService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(NewsController.class)
public class NewsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NewsService newsService;

    private NewsReadDTO createNewsRead() {
        NewsReadDTO read = new NewsReadDTO();
        read.setId(UUID.randomUUID());
        read.setTextNews("txt");
        return read;
    }

    @Test
    public void testGetNews() throws Exception {
        NewsReadDTO news = createNewsRead();

        Mockito.when(newsService.getNews(news.getId())).thenReturn(news);

        String resultJson = mvc.perform(get("/api/v1/news/{id}", news.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        NewsReadDTO actual = objectMapper.readValue(resultJson, NewsReadDTO.class);

        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(news, "createdAt", "updatedAt");

        Mockito.verify(newsService).getNews(news.getId());

    }

    @Test
    public void testGetNewsWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(News.class, wrongId);
        Mockito.when(newsService.getNews(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/news/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetNewsWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/news/00000001"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testCreateNews() throws Exception {
        NewsCreateDTO create = new NewsCreateDTO();
        create.setTextNews("txt");

        NewsReadDTO read = createNewsRead();

        Mockito.when(newsService.createNews(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/news")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        NewsReadDTO actual = objectMapper.readValue(resultJson, NewsReadDTO.class);
        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchNews() throws Exception {
        NewsPatchDTO patch = new NewsPatchDTO();
        patch.setTextNews("txt");

        NewsReadDTO read = createNewsRead();

        Mockito.when(newsService.patchNews(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/news/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        NewsReadDTO actual = objectMapper.readValue(resultJson, NewsReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testPutNews() throws Exception {
        NewsPutDTO putDTO = new NewsPutDTO();
        putDTO.setTextNews("txt");

        NewsReadDTO read = createNewsRead();

        Mockito.when(newsService.updateNews(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/news/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        NewsReadDTO actual = objectMapper.readValue(resultJson, NewsReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteNews() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/news/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(newsService).deleteNews(id);
    }


}