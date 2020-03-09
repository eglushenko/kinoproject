package com.solvve.lab.kinoproject.dto;

import com.solvve.lab.kinoproject.enums.RateMPAA;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class FilmReadExtendedDTO {
    private UUID id;
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
