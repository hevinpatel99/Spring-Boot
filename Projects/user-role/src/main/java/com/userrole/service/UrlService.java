package com.userrole.service;

import com.userrole.requestDto.UrlRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

/**
 * @author Hevin Mulani
 * Service interface for url related operations.
 */
public interface UrlService {
    ResponseEntity<ApiResponseDto> insertUrl(UrlRequestDto urlRequestDto);

    ResponseEntity<ApiResponseDto> getAllUrls();
}
