package com.solvve.lab.kinoproject.dto.customer;

import com.solvve.lab.kinoproject.enums.Gender;
import com.solvve.lab.kinoproject.enums.Role;
import lombok.Data;

@Data
public class CustomerCreateDTO {

    private String login;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private Gender gender;

}
