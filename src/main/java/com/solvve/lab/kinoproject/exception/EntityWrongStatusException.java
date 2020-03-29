package com.solvve.lab.kinoproject.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EntityWrongStatusException extends RuntimeException {

    public EntityWrongStatusException(Class entityClass, String status) {
        super(String.format("Entity %s with status=%s is error in status ", entityClass.getSimpleName(), status));
    }
}
