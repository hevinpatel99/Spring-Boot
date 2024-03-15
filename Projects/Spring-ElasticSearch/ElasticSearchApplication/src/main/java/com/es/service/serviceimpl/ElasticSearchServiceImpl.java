package com.es.service.serviceimpl;

import com.es.model.Movie;
import com.es.repository.ElasticSearchRepository;
import com.es.response.ApiResponse;
import com.es.service.ElasticSearchService;
import com.es.util.BaseMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    private final ElasticSearchRepository<?, ?> elasticSearchRepository;

    public ElasticSearchServiceImpl(ElasticSearchRepository<?, ?> elasticSearchRepository) {
        this.elasticSearchRepository = elasticSearchRepository;
    }

    @Override
    public ResponseEntity<?> getElasticData(HttpServletRequest request) {
        logger.info(" --- getElasticData --- ");
        List<Movie> movieList = elasticSearchRepository.getElasticData();
        if (BaseMethods.validateListNotNullOrNotEmpty(movieList)) {
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movieList, "All Movie get successFully",request.getRequestURI()));
        } else {
            return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.NOT_FOUND, null, "Not Found",request.getRequestURI()));
        }
    }

    @Override
    public ResponseEntity<?> getElasticDataBySearch(String searchContent, String searchText, HttpServletRequest request) {
        logger.info(" --- getElasticDataBySearch --- " );
        List<Movie> movieList = elasticSearchRepository.getElasticDataBySearch(searchContent, searchText);
        if (BaseMethods.validateListNotNullOrNotEmpty(movieList)) {
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movieList, "All Movie get successFully",request.getRequestURI()));
        } else {
            return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.NOT_FOUND, null, "Not Found",request.getRequestURI()));
        }
    }

    @Override
    public ResponseEntity<?> getElasticDataByKeyWord(String searchValue, HttpServletRequest request) {
        logger.info(" --- getElasticDataByKeyWord --- ");
        List<Movie> movieList = elasticSearchRepository.getElasticDataByKeyWord(searchValue);
        if (BaseMethods.validateListNotNullOrNotEmpty(movieList)) {
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, HttpStatus.OK, movieList, "All Movie get successFully",request.getRequestURI()));
        } else {
            return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.NOT_FOUND, null, "Not Found",request.getRequestURI()));
        }
    }

    @Override
    public ResponseEntity<?> deleteElasticData(String id, HttpServletRequest request) throws IOException {
        elasticSearchRepository.deleteElasticSearchData(id);
        return null;
    }

}
