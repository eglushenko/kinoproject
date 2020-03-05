package com.solvve.lab.kinoproject.dto.review;


import lombok.Data;

import java.util.UUID;

@Data
public class ReviewCreateDTO {
    private String reviewText;
    private UUID customerId;
}
