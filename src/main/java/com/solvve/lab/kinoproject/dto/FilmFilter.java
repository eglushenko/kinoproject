package com.solvve.lab.kinoproject.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class FilmFilter {
    private Instant lastUpdate;
    private Instant realiseYear;
    private int length;
}
