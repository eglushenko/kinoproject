package com.solvve.lab.kinoproject.dto.film;

import com.solvve.lab.kinoproject.enums.RateMPAA;
import lombok.Data;

import java.time.Instant;

@Data
public class FilmCreateDTO {
    private String title;
    private String country;
    private String lang;
    private Double averageRate;
    private Integer length;
    private Instant lastUpdate;
    private Instant realiseYear;
    private String category;
    private String filmText;
    private RateMPAA mpaa;
}
