package com.userservices.exception;

import com.userservices.responseDto.ErrorResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> customValidationErrorHandling(MethodArgumentNotValidException exception, HttpRequest request) {
        List<Object> errors = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.ok(new ErrorResponseDto(HttpStatus.BAD_REQUEST, "Validation Error", errors, request.getURI().getPath()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> customExceptionErrorHandling(MethodArgumentNotValidException exception, HttpRequest request) {
        List<Object> errors = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.ok(new ErrorResponseDto(HttpStatus.BAD_REQUEST, "Validation Error", errors, request.getURI().getPath()));
    }


}
