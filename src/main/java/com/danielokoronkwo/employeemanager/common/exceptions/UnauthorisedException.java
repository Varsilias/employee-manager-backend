package com.danielokoronkwo.employeemanager.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorisedException extends BaseException {

    public UnauthorisedException(String message) {
        super(message);
    }
}
