package com.solvve.lab.kinoproject.dto.like;

import com.solvve.lab.kinoproject.enums.LikedObjectType;
import lombok.Data;

import java.util.UUID;

@Data
public class LikeCreateDTO {

    private Boolean like;

    private UUID likedObjectId;

    private LikedObjectType type;
}
