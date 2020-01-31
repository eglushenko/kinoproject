package com.solvve.lab.kinoproject.dto.film;

import lombok.Data;

import java.time.Instant;

@Data
public class FilmPatchDTO {
    private String title;
    private String country;
    private String lang;
    private float rate;
    private int length;
    private Instant lastUpdate;
    private Instant realiseYear;
    private String category;
    private String filmText;
}
