package com.es.service;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ElasticSearchService {
    ResponseEntity<?> getElasticData(HttpServletRequest request);

    ResponseEntity<?> getElasticDataBySearch(String searchContent1, String searchText1, HttpServletRequest request);

    ResponseEntity<?> getElasticDataByKeyWord(String searchValue, HttpServletRequest request);

    ResponseEntity<?> deleteElasticData(String id, HttpServletRequest request) throws IOException;
}
