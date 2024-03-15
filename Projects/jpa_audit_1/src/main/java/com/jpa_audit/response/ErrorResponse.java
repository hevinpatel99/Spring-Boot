package com.jpa_audit.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {

    private int status;
    @JsonIgnore
    private HttpStatus httpStatus;
    private String errorMessage;
    private List<Object> errorDetail;


    public ErrorResponse(HttpStatus httpStatus, String errorMessage, List<Object> errorDetail) {
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.errorMessage = errorMessage;
        this.errorDetail = errorDetail;
    }

    public ErrorResponse(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.errorMessage = errorMessage;
    }
}
