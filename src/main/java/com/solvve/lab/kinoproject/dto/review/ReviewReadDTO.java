package com.solvve.lab.kinoproject.dto.review;


import com.solvve.lab.kinoproject.enums.ReviewStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ReviewReadDTO {
    private UUID id;
    private String reviewText;
    private ReviewStatus status;
    private UUID customerId;
    private UUID filmId;
    private Instant createdAt;
    private Instant updatedAt;
}
