package com.solvve.lab.kinoproject.dto.name;

import com.solvve.lab.kinoproject.enums.Gender;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class NameReadDTO {
    private UUID id;
    private String name;
    private LocalDate birthday;
    private LocalDate deathday;
    private Gender gender;
    private String biography;
    private Instant createdAt;
    private Instant updatedAt;

}
