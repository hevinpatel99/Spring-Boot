package com.organizationServices.exception;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

}
