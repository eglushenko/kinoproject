package com.solvve.lab.kinoproject.dto.cast;


import com.solvve.lab.kinoproject.enums.NameFilmRole;
import lombok.Data;

import java.util.UUID;

@Data
public class CastReadDTO {
    private UUID id;
    private String name;
    private NameFilmRole roleInFilm;
    private String nameRoleInFilm;
}