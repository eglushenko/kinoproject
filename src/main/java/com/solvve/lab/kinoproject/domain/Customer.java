package com.solvve.lab.kinoproject.domain;

import com.solvve.lab.kinoproject.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue
    @NotNull
    private UUID id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

}
