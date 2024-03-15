package com.es.controller;


import com.es.dto.MovieDTO;
import com.es.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/insertMovie")
    public ResponseEntity<?> insertMovie(@RequestBody @Valid MovieDTO movieDTO, HttpServletRequest request) throws IOException {
        return movieService.insertMovie(movieDTO,request);
    }

    @GetMapping("/getMovie")
    public ResponseEntity<?> getAllMovie(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "2") int size,HttpServletRequest request) {
        return movieService.getAllMovie(page, size,request);
    }
    
    @GetMapping("/getDataByMultiSearch")
    @Operation(
            summary = "Find BY FIELD",
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "searchContent1",
                            schema = @Schema(allowableValues = {"movie", "actor", "actress", "releaseYear"}),
                            description = "description "),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "searchContent2",
                            schema = @Schema(allowableValues = {"movie", "actor", "actress", "releaseYear"}),
                            description = "description")
            })
    public ResponseEntity<?> getDataByMultiSearch(@RequestParam(value = "searchContent1") String searchContent1, @RequestParam String searchText1, @RequestParam(value = "searchContent2", required = false) String searchContent2, @RequestParam(required = false, defaultValue = "") String searchText2,HttpServletRequest request) {
        return movieService.getDataByMultiSearch(searchContent1, searchText1, searchContent2, searchText2,request);
    }

    @GetMapping("/getAllMovieWithStatusTrue")
    public ResponseEntity<?> getAllMovieWithStatusTrue(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "2") int size,HttpServletRequest request) {
        return movieService.getAllMovieWithStatusTrue(page, size,request);
    }

    @GetMapping("/getAllMovie")
    public ResponseEntity<?> getAllMovie(HttpServletRequest request) {
        return movieService.getAllMovie(request);
    }


    @GetMapping("/searchByFields")
    @Operation(
            summary = "Find By FIELD",
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "searchContent",
                            schema = @Schema(allowableValues = {"movie", "actor", "actress", "releaseYear"}),
                            description = "description ")
            })
    public ResponseEntity<?> getMovieBySearchBy(@RequestParam(value = "searchContent") String searchContent, @RequestParam String searchText,HttpServletRequest request) {
        return movieService.getMovieBySearchBy(searchContent, searchText,request);
    }

    @PutMapping("/updateMovie/{movieName}")
     public ResponseEntity<?> updateMovie(@RequestBody @Valid MovieDTO movieDTO, @PathVariable String movieName,HttpServletRequest request) throws IOException {
        return movieService.updateMovie(movieDTO, movieName,request);
    }

    @DeleteMapping("/removeMovie/{movieName}")
    public ResponseEntity<?> removeMovie(@PathVariable String movieName,HttpServletRequest request) throws IOException {
        return movieService.removeMovie(movieName,request);
    }

    @PostMapping("/activeMovie")
    public ResponseEntity<?> activeMovie(@RequestParam String movieName,HttpServletRequest request) throws IOException {
        return movieService.activeMovie(movieName,request);
    }

}
