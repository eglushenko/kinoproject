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
        return reviewRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Review.class, id));
    }

    public ReviewReadDTO getReview(UUID id) {
        Review review = getReviewRequired(id);
        return translationService.translate(review, ReviewReadDTO.class);
    }

    public ReviewReadDTO createReview(ReviewCreateDTO create) {
        Review review = translationService.translate(create, Review.class);
        review = reviewRepository.save(review);
        return translationService.translate(review, ReviewReadDTO.class);
    }

    public ReviewReadDTO patchReview(UUID id, ReviewPatchDTO patch) {
        Review review = getReviewRequired(id);
        translationService.map(patch, review);
        review = reviewRepository.save(review);
        return translationService.translate(review, ReviewReadDTO.class);
    }

    public ReviewReadDTO updateReview(UUID id, ReviewPutDTO put) {
        Review review = getReviewRequired(id);
        translationService.map(put, review);
        return translationService.translate(review, ReviewReadDTO.class);
    }

    public void deleteReview(UUID id) {
        reviewRepository.delete(getReviewRequired(id));
    }
}
