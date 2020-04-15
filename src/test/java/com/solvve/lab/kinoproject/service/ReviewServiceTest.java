package com.solvve.lab.kinoproject.service;

import com.solvve.lab.kinoproject.BaseTest;
import com.solvve.lab.kinoproject.domain.Customer;
import com.solvve.lab.kinoproject.domain.Film;
import com.solvve.lab.kinoproject.domain.Review;
import com.solvve.lab.kinoproject.dto.review.ReviewCreateDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPatchDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPutDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.CustomerRepository;
import com.solvve.lab.kinoproject.repository.FilmRepository;
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
    private FilmRepository filmRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReviewService reviewService;

    private Review createReview() {
        Customer c = createCustomer();
        Film f = createFilm();
        Review review = generateFlatEntityWithoutId(Review.class);
        review.setFilm(f);
        review.setCustomer(c);
        return reviewRepository.save(review);
    }

    private Film createFilm() {
        Film film = generateFlatEntityWithoutId(Film.class);
        return filmRepository.save(film);
    }

    private Customer createCustomer() {
        Customer customer = generateFlatEntityWithoutId(Customer.class);
        return customerRepository.save(customer);
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
        Film f = createFilm();
        Customer c = createCustomer();
        ReviewCreateDTO create = generateObject(ReviewCreateDTO.class);
        create.setCustomerId(c.getId());
        create.setFilmId(f.getId());
        ReviewReadDTO read = reviewService.createReview(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Review review = reviewRepository.findById(read.getId()).get();
        Assertions.assertThat(read)
                .isEqualToIgnoringGivenFields(review,
                        "filmId", "customerId", "createdAt", "updatedAt");
    }

    @Test
    public void testPatchReview() {
        Review review = createReview();
        Customer customer = createCustomer();
        Film film = createFilm();

        ReviewPatchDTO patch = generateObject(ReviewPatchDTO.class);
        patch.setCustomerId(customer.getId());
        patch.setFilmId(film.getId());
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
                        "film", "customer", "createdAt", "updatedAt");
    }

    @Test
    public void testPutReview() {
        Review review = createReview();
        Film film = createFilm();
        Customer customer = createCustomer();

        ReviewPutDTO put = generateObject(ReviewPutDTO.class);
        put.setFilmId(film.getId());
        put.setCustomerId(customer.getId());
        ReviewReadDTO read = reviewService.updateReview(review.getId(), put);

        Assertions.assertThat(put).isEqualToIgnoringGivenFields(read,
                "filmId", "customerId", "createdAt", "updatedAt");

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