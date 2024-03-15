package com.redisCache.RedisDemo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse implements Serializable {

    private boolean status;
    private int statusCode;
    @JsonIgnore
    private HttpStatus httpStatus;
    private Object data;
    private String messageDescription;
    private String path;

    public ApiResponse(boolean status, HttpStatus httpStatus, Object data, String messageDescription,String path) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
        this.data = data;
        this.messageDescription = messageDescription;
        this.path = path;
    }
}
