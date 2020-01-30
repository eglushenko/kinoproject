package com.solvve.lab.kinoproject.controller;

import com.solvve.lab.kinoproject.dto.review.ReviewCreateDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPatchDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewPutDTO;
import com.solvve.lab.kinoproject.dto.review.ReviewReadDTO;
import com.solvve.lab.kinoproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{id}")
    public ReviewReadDTO getReview(@PathVariable UUID id) {
        return reviewService.getReview(id);
    }

    @PostMapping
    public ReviewReadDTO createReview(@RequestBody ReviewCreateDTO create) {
        return reviewService.createReview(create);
    }

    @PatchMapping("/{id}")
    public ReviewReadDTO patchReview(@PathVariable UUID id, @RequestBody ReviewPatchDTO patch) {
        return reviewService.patchReview(id, patch);
    }

    @PutMapping("/{id}")
    public ReviewReadDTO putReview(@PathVariable UUID id, @RequestBody ReviewPutDTO put) {
        return reviewService.putReview(id, put);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
    }
}
