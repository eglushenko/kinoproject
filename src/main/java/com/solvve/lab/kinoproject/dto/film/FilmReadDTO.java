package com.solvve.lab.kinoproject.dto.film;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class FilmReadDTO {
    private UUID id;
    private String title;
    private String country;
    private String lang;
    private Float rate;
    private Integer length;
    private Instant lastUpdate;
    private Instant realiseYear;
    private String category;
    private String filmText;
    private Instant createdAt;
    private Instant updatedAt;


}
