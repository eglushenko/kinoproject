package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Review;
import com.solvve.lab.kinoproject.dto.review.ReviewCreateDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPatchDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPutDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewReadDTO;
import com.solvve.lab.kinoproject.enums.ReviewStatus;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class ReviewServiceTest extends BaseTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    private Review createReview() {
        Review review = new Review();
        review.setReviewText("some review");
        review.setStatus(ReviewStatus.NEW);
        return reviewRepository.save(review);
    }

    @Test
    public void testGetReview() {
        Review review = createReview();

        ReviewReadDTO read = reviewService.getReview(review.getId());
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(review,
                        "filmId", "customerId", "createdAt", "updatedAt");
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
        Assertions.assertThat(create)
                .isEqualToIgnoringGivenFields(read,
                        "filmId", "customerId", "createdAt", "updatedAt");
        Assert.assertNotNull(read.getId());

        Review review = reviewRepository.findById(read.getId()).get();
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(review,
                        "filmId", "customerId", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchReview() {
        Review review = createReview();

        ReviewPatchDTO patch = new ReviewPatchDTO();
        patch.setReviewText("txt");
        patch.setStatus(ReviewStatus.NEW);
        ReviewReadDTO read = reviewService.patchReview(review.getId(), patch);

        Assertions.assertThat(patch)
                .isEqualToIgnoringGivenFields(read,
                        "filmId", "customerId", "createdAt", "updatedAt");

        review = reviewRepository.findById(read.getId()).get();
        Assertions.assertThat(review)
                .isEqualToIgnoringGivenFields(read,
                        "film", "customer", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchReviewEmptyPatch() {
        Review review = createReview();

        ReviewPatchDTO patch = new ReviewPatchDTO();

        ReviewReadDTO read = reviewService.patchReview(review.getId(), patch);

        Assert.assertNotNull(read.getReviewText());

        Review afterUpdate = reviewRepository.findById(read.getId()).get();

        Assert.assertNotNull(afterUpdate.getReviewText());

        Assertions.assertThat(review)
                .isEqualToIgnoringGivenFields(afterUpdate,
                        "filmId", "customerId", "createdAt", "updatedAt");
    }

    @Test
    public void testPutReview() {
        Review review = createReview();

        ReviewPutDTO put = new ReviewPutDTO();
        put.setReviewText("some review");
        put.setStatus(ReviewStatus.NEW);
        ReviewReadDTO read = reviewService.updateReview(review.getId(), put);

        Assertions.assertThat(put).isEqualToComparingFieldByField(read);

        review = reviewRepository.findById(read.getId()).get();
        Assertions.assertThat(review)
                .isEqualToIgnoringGivenFields(read,
                        "film", "customer", "createdAt", "updatedAt");
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