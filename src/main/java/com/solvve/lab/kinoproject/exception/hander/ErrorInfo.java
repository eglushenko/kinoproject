package com.solvve.lab.kinoproject.exception.hander;

import org.springframework.http.HttpStatus;

public class ErrorInfo {
    private final HttpStatus status;
    private final Class exeptionClass;
    private final String msg;

    public ErrorInfo(HttpStatus status, Class exeptionClass, String msg) {
        this.status = status;
        this.exeptionClass = exeptionClass;
        this.msg = msg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Class getExeptionClass() {
        return exeptionClass;
    }

    public String getMsg() {
        return msg;
    }
}
