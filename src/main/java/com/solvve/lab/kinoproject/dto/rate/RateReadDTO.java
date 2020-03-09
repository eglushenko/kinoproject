package com.solvve.lab.kinoproject.dto.rate;

import com.solvve.lab.kinoproject.enums.RateObjectType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;


@Data
public class RateReadDTO {
    private UUID id;

    private Double rate;

    private UUID ratedObjectId;

    private RateObjectType type;

    private UUID customerId;

    private Instant createdAt;

    private Instant updatedAt;
}
