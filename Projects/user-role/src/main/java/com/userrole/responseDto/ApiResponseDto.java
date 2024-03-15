package com.userrole.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;


/**
 *
 * @author Hevin Mulani
 * response dto class for Api responses.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponseDto {
    private int status;
    private String message;
    private Object data;
    @JsonIgnore
    private HttpStatus httpStatus;

    public ApiResponseDto(HttpStatus httpStatus, String message, Object data) {
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.message = message;
        this.data = data;
    }

    public ApiResponseDto(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.message = message;
    }
}
