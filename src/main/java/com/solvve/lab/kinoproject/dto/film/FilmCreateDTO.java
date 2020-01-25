package com.solvve.lab.kinoproject.dto.film;

import lombok.Data;

import java.time.Instant;

@Data
public class FilmCreateDTO {
    private String title;
    private String country;
    private String lang;
    private float rate;
    private int length;
    private Instant lastUpdate;
    private String actor;
    private String category;
    private String filmText;


}
