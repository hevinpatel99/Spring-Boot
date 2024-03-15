package com.userrole.exception;


import com.userrole.responseDto.ErrorResponseDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Hevin Mulani
 * GlobalExceptionHandler class for handle global errors.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> customValidationErrorHandling(MethodArgumentNotValidException exception) {
        List<Object> errors = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return ResponseEntity.ok(new ErrorResponseDto(HttpStatus.BAD_REQUEST, "Validation Error", errors));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException exception) {
        String exceptionMessage = exception.getExceptionMessage();
        return ResponseEntity.ok(new ErrorResponseDto(HttpStatus.UNAUTHORIZED, "Unauthorized access", Collections.singletonList(exceptionMessage)));
    }

}
