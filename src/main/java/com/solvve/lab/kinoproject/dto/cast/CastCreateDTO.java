package com.solvve.lab.kinoproject.dto.cast;

import com.solvve.lab.kinoproject.enums.NameFilmRole;
import lombok.Data;


@Data
public class CastCreateDTO {
    private String name;
    private NameFilmRole roleInFilm;
    private String nameRoleInFilm;
}