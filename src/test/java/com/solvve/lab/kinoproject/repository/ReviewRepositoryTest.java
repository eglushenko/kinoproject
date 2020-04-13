package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Review;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;


public class ReviewRepositoryTest extends BaseTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private Review createReview() {
        Review review = new Review();
        review.setReviewText("some review");
        return reviewRepository.save(review);
    }

    @Test
    public void testReviewCreateDate() {
        Review review = createReview();
        Assert.assertNotNull(review.getCreatedAt());

        Instant createDateBeforeLoad = review.getCreatedAt();

        review = reviewRepository.findById(review.getId()).get();

        Instant createDateAfterLoad = review.getCreatedAt();

        Assertions.assertThat(createDateBeforeLoad).isEqualTo(createDateAfterLoad);
    }

    @Test
    public void testReviewUpdateDate() {
        Review review = createReview();
        Assert.assertNotNull(review.getUpdatedAt());

        Instant updateDateBeforeLoad = review.getUpdatedAt();

        review.setReviewText("some txt");
        review = reviewRepository.save(review);
        review = reviewRepository.findById(review.getId()).get();

        Instant updateDateAfterLoad = review.getUpdatedAt();

        Assert.assertNotNull(updateDateAfterLoad);
        Assertions.assertThat(updateDateAfterLoad).isAfter(updateDateBeforeLoad);
    }


}