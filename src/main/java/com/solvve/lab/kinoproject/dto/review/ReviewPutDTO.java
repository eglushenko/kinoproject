package com.solvve.lab.kinoproject.dto.review;


import lombok.Data;

import java.util.UUID;

@Data
public class ReviewPutDTO {
    private String reviewText;
    private UUID customerId;
    private UUID filmId;


}
