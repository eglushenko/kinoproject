package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Review;
import com.solvve.lab.kinoproject.dto.review.ReviewCreateDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPatchDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPutDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from review", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ReviewServiceTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    private Review createReview() {
        Review review = new Review();
        review.setReviewText("some review");
        return reviewRepository.save(review);
    }

    @Test
    public void testGetReview() {
        Review review = createReview();

        ReviewReadDTO read = reviewService.getReview(review.getId());
        Assertions.assertThat(read).isEqualToComparingFieldByField(review);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetReviewWrongId() {
        reviewService.getReview(UUID.randomUUID());

    }

    @Test
    public void testCreateReview() {
        ReviewCreateDTO create = new ReviewCreateDTO();
        create.setReviewText("some txt");
        ReviewReadDTO read = reviewService.createReview(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Review review = reviewRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToComparingFieldByField(review);
    }

    @Test
    public void testPatchReview() {
        Review review = createReview();

        ReviewPatchDTO patch = new ReviewPatchDTO();
        patch.setReviewText("txt");
        ReviewReadDTO read = reviewService.patchReview(review.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        review = reviewRepository.findById(read.getId()).get();
        Assertions.assertThat(review).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testPatchReviewEmptyPatch() {
        Review review = createReview();

        ReviewPatchDTO patch = new ReviewPatchDTO();

        ReviewReadDTO read = reviewService.patchReview(review.getId(), patch);

        Assert.assertNotNull(read.getReviewText());

        Review afterUpdate = reviewRepository.findById(read.getId()).get();

        Assert.assertNotNull(afterUpdate.getReviewText());

        Assertions.assertThat(review).isEqualToComparingFieldByField(afterUpdate);
    }

    @Test
    public void testPutReview() {
        Review review = createReview();

        ReviewPutDTO put = new ReviewPutDTO();
        put.setReviewText("some review");
        ReviewReadDTO read = reviewService.putReview(review.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        review = reviewRepository.findById(read.getId()).get();
        Assertions.assertThat(review).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testDeleteReview() {
        Review review = createReview();

        reviewService.deleteReview(review.getId());

        Assert.assertFalse(reviewRepository.existsById(review.getId()));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteReviewNotFoundId() {
        reviewService.deleteReview(UUID.randomUUID());
    }

}