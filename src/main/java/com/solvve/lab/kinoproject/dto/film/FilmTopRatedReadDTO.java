package com.solvve.lab.kinoproject.dto.film;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FilmTopRatedReadDTO {
    private UUID id;
    private String title;
    private Double averageRate;
    private long count;

}
