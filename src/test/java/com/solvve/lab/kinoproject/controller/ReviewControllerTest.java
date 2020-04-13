package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.domain.Review;
import com.solvve.lab.kinoproject.dto.review.ReviewCreateDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPatchDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPutDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.service.ReviewService;
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

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest extends BaseControllerTest {

    @MockBean
    private ReviewService reviewService;

    private ReviewReadDTO createReviewRead() {
        ReviewReadDTO read = new ReviewReadDTO();
        read.setId(UUID.randomUUID());
        read.setReviewText("txt");
        return read;
    }

    @Test
    public void testGetReview() throws Exception {
        ReviewReadDTO review = createReviewRead();

        Mockito.when(reviewService.getReview(review.getId())).thenReturn(review);

        String resultJson = mvc.perform(get("/api/v1/reviews/{id}", review.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ReviewReadDTO actual = objectMapper.readValue(resultJson, ReviewReadDTO.class);

        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(review, "createdAt", "updatedAt");

        Mockito.verify(reviewService).getReview(review.getId());

    }

    @Test
    public void testGetReviewWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Review.class, wrongId);
        Mockito.when(reviewService.getReview(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/reviews/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetReviewWrongIdFormat() throws Exception {
        String resultJson = String.valueOf(mvc.perform(get("/api/v1/reviews/00000001"))
                .andReturn().getResponse().getStatus());
        Assert.assertTrue(resultJson.contains("400"));
    }

    @Test
    public void testCreateReview() throws Exception {
        ReviewCreateDTO create = new ReviewCreateDTO();
        create.setReviewText("txt");

        ReviewReadDTO read = createReviewRead();

        Mockito.when(reviewService.createReview(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/reviews")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ReviewReadDTO actual = objectMapper.readValue(resultJson, ReviewReadDTO.class);
        Assertions.assertThat(actual)
                .isEqualToIgnoringGivenFields(read, "createdAt", "updatedAt");
    }

    @Test
    public void testPatchReview() throws Exception {
        ReviewPatchDTO patch = new ReviewPatchDTO();
        patch.setReviewText("txt");

        ReviewReadDTO read = createReviewRead();

        Mockito.when(reviewService.patchReview(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/reviews/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ReviewReadDTO actual = objectMapper.readValue(resultJson, ReviewReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testPutReview() throws Exception {
        ReviewPutDTO putDTO = new ReviewPutDTO();
        putDTO.setReviewText("txt");

        ReviewReadDTO read = createReviewRead();

        Mockito.when(reviewService.updateReview(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/reviews/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ReviewReadDTO actual = objectMapper.readValue(resultJson, ReviewReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteReview() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/reviews/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(reviewService).deleteReview(id);
    }


}