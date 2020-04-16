package com.solvve.lab.kinoproject.dto.genere;


import lombok.Data;

import java.util.UUID;

@Data
public class GenereReadDTO {
    private UUID id;

    private String genereName;

    private String genreDescription;
}
