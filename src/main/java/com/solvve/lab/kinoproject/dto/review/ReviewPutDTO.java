package com.solvve.lab.kinoproject.dto.review;


import com.solvve.lab.kinoproject.enums.ReviewStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewPutDTO {
    private String reviewText;
    private ReviewStatus status;
    private UUID customerId;
    private UUID filmId;


}
