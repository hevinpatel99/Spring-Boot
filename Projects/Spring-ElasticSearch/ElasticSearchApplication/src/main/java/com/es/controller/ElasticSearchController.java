package com.es.controller;

import com.es.service.ElasticSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/esMovie")
public class ElasticSearchController {

    private final ElasticSearchService elasticSearchService;

    public ElasticSearchController(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    @GetMapping("/getElasticData")
    public ResponseEntity<?> getElasticData(HttpServletRequest request) {
        return elasticSearchService.getElasticData(request);
    }

    @GetMapping("/getElasticDataBySearch")
    @Operation(
            summary = "Find BY FIELD",
            parameters = {
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "searchContent1",
                            schema = @Schema(allowableValues = {"movie", "actor", "actress", "releaseYear"}),
                            description = "description ")
            })
    public ResponseEntity<?> getElasticDataBySearch(@RequestParam(value = "searchContent1") String searchContent1, @RequestParam String searchText1, HttpServletRequest request) {
        return elasticSearchService.getElasticDataBySearch(searchContent1, searchText1, request);
    }


    @GetMapping("/getElasticDataByKeyWord")
    public ResponseEntity<?> getElasticDataByKeyWord(@RequestParam(value = "searchValue") String searchValue, HttpServletRequest request) {
        return elasticSearchService.getElasticDataByKeyWord(searchValue, request);
    }

    @DeleteMapping("/deleteData/{id}")
    public ResponseEntity<?> deleteElasticData(@PathVariable(value = "id") String id, HttpServletRequest request) throws IOException {
        return elasticSearchService.deleteElasticData(id, request);
    }


}

