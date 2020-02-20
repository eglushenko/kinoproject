package com.solvve.lab.kinoproject.dto.cast;


import com.solvve.lab.kinoproject.enums.NameFilmRole;
import lombok.Data;

import java.util.UUID;

@Data
public class CastPutDTO {
    private NameFilmRole roleInFilm;
    private String nameRoleInFilm;
    private UUID nameId;
    private UUID filmId;
}
