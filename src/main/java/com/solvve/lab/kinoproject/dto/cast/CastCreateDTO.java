package com.solvve.lab.kinoproject.dto.cast;

import com.solvve.lab.kinoproject.enums.NameFilmRole;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
public class CastCreateDTO {

    @NotNull
    private NameFilmRole roleInFilm;

    @NotNull
    private String nameRoleInFilm;

    @NotNull
    private UUID nameId;

    @NotNull
    private UUID filmId;
}
