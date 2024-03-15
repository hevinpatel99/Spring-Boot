package com.userrole.controller;

import com.userrole.requestDto.UrlRequestDto;
import com.userrole.requestDto.UserRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Hevin Mulani
 * Controller for handling Url-related endpoints.
 */
@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    /**
     * Endpoint for inserting Url.
     *
     * @param urlRequestDto
     * @return
     */
    @PostMapping("/insertUrl")
    public ResponseEntity<ApiResponseDto> insertUrl(@RequestBody @Valid UrlRequestDto urlRequestDto) {
        return urlService.insertUrl(urlRequestDto);
    }

    /**
     * Endpoint for retrieve all Urls.
     */
    @GetMapping("/getAllUrls")
    public ResponseEntity<ApiResponseDto> getAllUrls() {
        return urlService.getAllUrls();
    }
}
