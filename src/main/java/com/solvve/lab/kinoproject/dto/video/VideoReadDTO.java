package com.solvve.lab.kinoproject.dto.video;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;


@Data
public class VideoReadDTO {
    private UUID id;
    private String videoLink;
    private Instant createdAt;
    private Instant updatedAt;
}
