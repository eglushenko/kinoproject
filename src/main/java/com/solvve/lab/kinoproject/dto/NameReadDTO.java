package com.solvve.lab.kinoproject.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NameReadDTO {
    private UUID id;
    private String firstName;
    private String lastName;

    public UUID getId() {
        return id;
    }


}
