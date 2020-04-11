package com.solvve.lab.kinoproject.dto.cast;


import com.solvve.lab.kinoproject.enums.NameFilmRole;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CastReadDTO {
    private UUID id;
    private NameFilmRole roleInFilm;
    private String nameRoleInFilm;
    private UUID nameId;
    private UUID filmId;
    private Instant createdAt;
    private Instant updatedAt;
}
