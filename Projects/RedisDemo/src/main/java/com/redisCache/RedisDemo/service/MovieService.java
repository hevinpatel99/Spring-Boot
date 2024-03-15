package com.redisCache.RedisDemo.service;

import com.redisCache.RedisDemo.dto.MovieDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface MovieService {
    ResponseEntity<?> insertMovie(MovieDTO movieDTO, HttpServletRequest request);

    ResponseEntity<?> getAllMovie(int page, int size, HttpServletRequest request);

    ResponseEntity<?> getDataByMultiSearch(String searchContent1, String searchText1, String searchContent2, String searchText2, HttpServletRequest request);

    ResponseEntity<?> getAllMovieWithStatusTrue(int page, int size, HttpServletRequest request);

    ResponseEntity<?> getMovieBySearchBy(String searchContent, String searchText, HttpServletRequest request);

    ResponseEntity<?> updateMovie(MovieDTO movieDTO, String movieName, HttpServletRequest request);

    ResponseEntity<?> activeMovie(String movieName, HttpServletRequest request);

    ResponseEntity<?> removeMovie(String movieName, HttpServletRequest request);

    ResponseEntity<?> getAllMovie(HttpServletRequest request);

    ResponseEntity<?> getMovieByMovieName(String movieName, HttpServletRequest request);

}
