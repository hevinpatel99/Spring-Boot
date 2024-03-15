package com.userrole.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;


/**
 * @author Hevin Mulani
 *
 * response class for API responses.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse {

    private boolean status;
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
}
