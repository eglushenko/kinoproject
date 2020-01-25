package com.solvve.lab.kinoproject.dto.customer;

import lombok.Data;

@Data
public class CustomerCreateDTO {
    private String login;
    private String firstName;
    private String lastName;
    private String email;


}
