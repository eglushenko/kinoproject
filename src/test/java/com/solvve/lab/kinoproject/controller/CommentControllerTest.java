package com.solvve.lab.kinoproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvve.lab.kinoproject.dto.CommentReadDTO;
import com.solvve.lab.kinoproject.service.CommentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static com.solvve.lab.kinoproject.enums.CommentStatus.UNCHECKED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    private CommentReadDTO createCommentRead() {
        CommentReadDTO read = new CommentReadDTO();
        read.setId(UUID.randomUUID());
        read.setCommentText("comment text");
        read.setPostedDate(LocalDate.of(2020, 1, 22));
        read.setCommentStatus(UNCHECKED);
        read.setRate(1.1F);
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


}