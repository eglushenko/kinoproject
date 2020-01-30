package com.solvve.lab.kinoproject.service;


import com.solvve.lab.kinoproject.domain.Review;
import com.solvve.lab.kinoproject.dto.review.ReviewCreateDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPatchDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPutDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewReadDTO;
import com.solvve.lab.kinoproject.exception.EntityNotFoundException;
import com.solvve.lab.kinoproject.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TranslationService translationService;

    public Review getReviewRequired(UUID id) {
        return reviewRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(Review.class, id));
    }

    public ReviewReadDTO getReview(UUID id) {
        Review review = getReviewRequired(id);
        return translationService.toReadReview(review);
    }

    public ReviewReadDTO createReview(ReviewCreateDTO create) {
        Review review = translationService.toEntityReview(create);
        review = reviewRepository.save(review);
        return translationService.toReadReview(review);
    }

    public ReviewReadDTO patchReview(UUID id, ReviewPatchDTO patch) {
        Review review = getReviewRequired(id);
        translationService.patchEntityReview(patch, review);
        review = reviewRepository.save(review);
        return translationService.toReadReview(review);
    }

    public ReviewReadDTO putReview(UUID id, ReviewPutDTO put) {
        Review review = getReviewRequired(id);
        translationService.putEntityReview(put, review);
        return translationService.toReadReview(review);
    }

    public void deleteReview(UUID id) {
        reviewRepository.delete(getReviewRequired(id));
    }
}
