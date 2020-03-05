package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvve.lab.kinoproject.domain.Like;
import com.solvve.lab.kinoproject.dto.like.LikeCreateDTO;
import com.solvve.lab.kinoproject.dto.like.LikePatchDTO;
import com.solvve.lab.kinoproject.dto.like.LikePutDTO;
import com.solvve.lab.kinoproject.dto.like.LikeReadDTO;
import com.solvve.lab.kinoproject.enums.LikedObjectType;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.LikeService;
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
@WebMvcTest(LikeController.class)
public class LikeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LikeService likeService;

    private LikeReadDTO createLikeRead() {
        LikeReadDTO read = new LikeReadDTO();
        read.setId(UUID.randomUUID());
        read.setLike(true);
        read.setLikedObjectId(UUID.randomUUID());
        read.setType(LikedObjectType.COMMENT);
        return read;
    }

    @Test
    public void testGetLike() throws Exception {
        LikeReadDTO like = createLikeRead();

        Mockito.when(likeService.getLike(like.getId())).thenReturn(like);

        String resultJson = mvc.perform(get("/api/v1/likes/{id}", like.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LikeReadDTO actual = objectMapper.readValue(resultJson, LikeReadDTO.class);

        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(like, "createdAt", "updatedAt");

        Mockito.verify(likeService).getLike(like.getId());

    }

    @Test
    public void testGetLikeWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Like.class, wrongId);
        Mockito.when(likeService.getLike(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/likes/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetLikeWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/likes/00000001"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testCreateLike() throws Exception {
        LikeCreateDTO create = new LikeCreateDTO();
        create.setLike(false);
        create.setLikedObjectId(UUID.randomUUID());
        create.setType(LikedObjectType.COMMENT);

        LikeReadDTO read = createLikeRead();

        Mockito.when(likeService.createLike(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/likes")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LikeReadDTO actual = objectMapper.readValue(resultJson, LikeReadDTO.class);
        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchLike() throws Exception {
        LikePatchDTO patch = new LikePatchDTO();
        patch.setLike(true);
        patch.setType(LikedObjectType.COMMENT);
        patch.setLikedObjectId(UUID.randomUUID());

        LikeReadDTO read = createLikeRead();

        Mockito.when(likeService.patchLike(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/likes/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LikeReadDTO actual = objectMapper.readValue(resultJson, LikeReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testPutLike() throws Exception {
        LikePutDTO putDTO = new LikePutDTO();
        putDTO.setType(LikedObjectType.FILM);
        putDTO.setLikedObjectId(UUID.randomUUID());
        putDTO.setLike(true);

        LikeReadDTO read = createLikeRead();

        Mockito.when(likeService.updateLike(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/likes/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LikeReadDTO actual = objectMapper.readValue(resultJson, LikeReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteLike() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/likes/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(likeService).deleteLike(id);
    }


}