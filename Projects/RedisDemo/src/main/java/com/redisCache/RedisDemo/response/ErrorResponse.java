package com.redisCache.RedisDemo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ErrorResponse implements Serializable {

    private int status;

    @JsonIgnore
    private HttpStatus httpStatus;

    private String errorMessage;
    private List<Object> errorDetail;
    private String path;


    public ErrorResponse(HttpStatus httpStatus, String errorMessage, List<Object> errorDetail, String path) {
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.errorMessage = errorMessage;
        this.errorDetail = errorDetail;
        this.path = path;
    }
}
