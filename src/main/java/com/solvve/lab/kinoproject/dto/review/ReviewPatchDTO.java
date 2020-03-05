package com.solvve.lab.kinoproject.dto.review;


import lombok.Data;

import java.util.UUID;

@Data
public class ReviewPatchDTO {
    private String reviewText;
    private UUID customerId;
}
