package com.solvve.lab.kinoproject.dto.like;

import com.solvve.lab.kinoproject.enums.LikedObjectType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;


@Data
public class LikeReadDTO {

    private UUID id;

    private Boolean like;

    private UUID likedObjectId;

    private LikedObjectType type;

    private Instant createdAt;

    private Instant updatedAt;
}
