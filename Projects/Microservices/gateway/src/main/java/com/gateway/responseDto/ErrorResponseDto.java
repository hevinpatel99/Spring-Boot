package com.gateway.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponseDto {


    private int status;
    @JsonIgnore
    private HttpStatus httpStatus;
    private String errorMessage;
    private List<Object> errorDetail;
    private String uri;


    public ErrorResponseDto(HttpStatus httpStatus, String errorMessage, List<Object> errorDetail, String uri) {
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.errorMessage = errorMessage;
        this.errorDetail = errorDetail;
        this.uri = uri;
    }


}



