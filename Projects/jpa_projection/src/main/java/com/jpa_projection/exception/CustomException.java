package com.jpa_projection.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author Hevin Mulani
 * Custom Exception class
 */
@Getter
@Setter
public class CustomException extends RuntimeException {


    private String exceptionMessage;
    private HttpStatus httpStatus;

    public CustomException(String exceptionMessage, HttpStatus httpStatus) {
        this.exceptionMessage = exceptionMessage;
        this.httpStatus = httpStatus;
    }

}
