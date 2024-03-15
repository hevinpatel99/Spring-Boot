package com.es.exception;

import com.es.response.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> customValidationErrorHandling(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<Object> errors = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST,"Validation Error", errors, request.getRequestURI()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandling(CustomException exception, HttpServletRequest request) {
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST, "Error", Collections.singletonList(exception.getMessage()), request.getRequestURI()));
    }
}
