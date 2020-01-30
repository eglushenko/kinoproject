package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.domain.Review;
import com.solvve.lab.kinoproject.dto.review.ReviewReadDTO;
import com.solvve.lab.kinoproject.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;


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

}