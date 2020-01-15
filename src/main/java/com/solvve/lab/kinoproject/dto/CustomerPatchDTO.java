package com.solvve.lab.kinoproject.dto;

import com.solvve.lab.kinoproject.enums.Role;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class CustomerPatchDTO {
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
}
