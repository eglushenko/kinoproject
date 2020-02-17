package com.solvve.lab.kinoproject.dto.review;


import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ReviewReadDTO {
    private UUID id;
    private String reviewText;
    private Instant createdAt;
    private Instant updatedAt;
}
