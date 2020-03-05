package com.solvve.lab.kinoproject.dto.scene;

import lombok.Data;

import java.util.UUID;

@Data
public class ScenePatchDTO {
    private String sceneLink;

    private UUID filmId;

}
