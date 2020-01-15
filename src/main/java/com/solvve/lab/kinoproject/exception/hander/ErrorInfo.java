package com.solvve.lab.kinoproject.exception.hander;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorInfo {
    private final HttpStatus status;
    private final Class exeptionClass;
    private final String msg;

    public ErrorInfo(HttpStatus status, Class exeptionClass, String msg) {
        this.status = status;
        this.exeptionClass = exeptionClass;
        this.msg = msg;
    }


}
