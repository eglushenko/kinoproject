package com.solvve.lab.kinoproject.dto.rate;


import com.solvve.lab.kinoproject.enums.RateObjectType;
import lombok.Data;

import java.util.UUID;

@Data
public class RatePutDTO {

    private Double rate;

    private UUID ratedObjectId;

    private RateObjectType type;

    private UUID customerId;
}
