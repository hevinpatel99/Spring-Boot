package com.userrole.service;


import com.userrole.requestDto.UserRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;


/**
 * @author Hevin Mulani
 * Service interface for user related operations.
 */
public interface UserService {
    ResponseEntity<ApiResponseDto> insertUser(UserRequestDto userRequestDto);

    ResponseEntity<ApiResponseDto> getAllUser();

}
