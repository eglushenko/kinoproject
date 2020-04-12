package com.solvve.lab.kinoproject.event;


import com.solvve.lab.kinoproject.enums.ReviewStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewStatusChangedEvent {
    private UUID reviewId;
    private ReviewStatus newStatus;
}
