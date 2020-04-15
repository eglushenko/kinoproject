package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.domain.Comment;
import com.solvve.lab.kinoproject.dto.comment.CommentCreateDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPatchDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentPutDTO;
import com.solvve.lab.kinoproject.dto.comment.CommentReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.CommentService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest extends BaseControllerTest {

    @MockBean
    private CommentService commentService;

    private CommentReadDTO createCommentRead() {
        CommentReadDTO read = generateObject(CommentReadDTO.class);
        return read;
    }

    @Test
    public void testGetComment() throws Exception {
        CommentReadDTO comment = createCommentRead();

        Mockito.when(commentService.getComment(comment.getId())).thenReturn(comment);

        String resultJson = mvc.perform(get("/api/v1/comments/{id}", comment.getId()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        CommentReadDTO actual = objectMapper.readValue(resultJson, CommentReadDTO.class);
        Assertions.assertThat(actual).isEqualToComparingFieldByField(comment);

        Mockito.verify(commentService).getComment(comment.getId());
    }

    @Test
    public void testGetCommentWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Comment.class, wrongId);

        Mockito.when(commentService.getComment(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/comments/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetCommentWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/comments/22222"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));

    }

    @Test
    public void testCreateComment() throws Exception {
        CommentCreateDTO create = generateObject(CommentCreateDTO.class);

        CommentReadDTO read = createCommentRead();

        Mockito.when(commentService.createComment(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/comments")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CommentReadDTO commentReadDTO = objectMapper.readValue(resultJson, CommentReadDTO.class);
        Assertions.assertThat(commentReadDTO).isEqualToComparingFieldByField(read);

    }

    @Test
    public void testPatchComment() throws Exception {
        CommentPatchDTO patchDTO = generateObject(CommentPatchDTO.class);

        CommentReadDTO read = createCommentRead();

        Mockito.when(commentService.patchComment(read.getId(), patchDTO)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/comments/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CommentReadDTO actual = objectMapper.readValue(resultJson, CommentReadDTO.class);
        Assert.assertEquals(read, actual);

    }

    @Test
    public void testPutComment() throws Exception {
        CommentPutDTO putDTO = generateObject(CommentPutDTO.class);

        CommentReadDTO read = createCommentRead();

        Mockito.when(commentService.updateComment(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/comments/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CommentReadDTO actualComment = objectMapper.readValue(resultJson, CommentReadDTO.class);
        Assert.assertEquals(read, actualComment);
    }

    @Test
    public void testDeleteComment() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/comments/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(commentService).deleteComment(id);
    }


}