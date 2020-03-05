package com.solvve.lab.kinoproject.dto.scene;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;


@Data
public class SceneReadDTO {
    private UUID id;

    private String sceneLink;

    private UUID filmId;

    private Instant createdAt;

    private Instant updatedAt;
}
