package com.solvve.lab.kinoproject.dto.customer;

import com.solvve.lab.kinoproject.enums.Gender;
import com.solvve.lab.kinoproject.enums.Role;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class CustomerReadDTO {
    private UUID id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Gender gender;
    private Instant createdAt;
    private Instant updatedAt;

}
