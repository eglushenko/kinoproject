package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Review;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from review", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ReviewRepositoryTest {

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