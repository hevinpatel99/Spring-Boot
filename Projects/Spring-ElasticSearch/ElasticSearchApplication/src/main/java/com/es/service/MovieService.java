package com.es.service;


import com.es.dto.MovieDTO;
import com.es.exception.CustomException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public interface MovieService {
    ResponseEntity<?> insertMovie(MovieDTO movieDTO, HttpServletRequest request) throws CustomException, IOException;


    ResponseEntity<?> getAllMovie(int page, int size, HttpServletRequest request);

    ResponseEntity<?> removeMovie(String id, HttpServletRequest request) throws IOException;

    ResponseEntity<?> updateMovie(MovieDTO movieDTO, String movieName, HttpServletRequest request) throws IOException;

    ResponseEntity<?> getMovieBySearchBy(String searchContent, String searchText, HttpServletRequest request);

    ResponseEntity<?> getAllMovieWithStatusTrue(int page, int size, HttpServletRequest request);

    ResponseEntity<?> getDataByMultiSearch(String searchContent1, String searchText1, String searchContent2, String searchText2, HttpServletRequest request);

    ResponseEntity<?> activeMovie(String movieName, HttpServletRequest request) throws IOException;

    ResponseEntity<?> getAllMovie(HttpServletRequest request);
}
