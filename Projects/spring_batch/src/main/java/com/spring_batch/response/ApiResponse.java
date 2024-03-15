package com.spring_batch.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


/**
 * @author Hevin Mulani
 * <p>
 * response class for API responses.
 */
@Getter
@Setter
@ToString
public class ApiResponse {

    private boolean status = true;
    private int statusCode;
    @JsonIgnore
    private HttpStatus httpStatus;
    private Object data;
    private String messageDescription;

    public ApiResponse(boolean status, HttpStatus httpStatus, Object data, String messageDescription) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
        this.data = data;
        this.messageDescription = messageDescription;
    }


    public ApiResponse(HttpStatus httpStatus, String message, Object data) {
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
        this.messageDescription = message;
        this.data = data;
    }
}
