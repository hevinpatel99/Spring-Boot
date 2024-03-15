package com.ratingServices.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponseDto {
    private int status;
    @JsonIgnore
    private HttpStatus httpStatus;
    private String message;
    private Object data;


    public ApiResponseDto(HttpStatus httpStatus, String message, Object data) {
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.message = message;
        this.data = data;
    }
}
