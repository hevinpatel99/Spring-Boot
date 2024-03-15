package com.roles_privileges.exception;


import lombok.var;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {


    private HttpStatus httpStatus;

    public CustomException(String exceptionMessage, HttpStatus httpStatus) {
        super(exceptionMessage);
    }

}
