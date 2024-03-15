package com.es.repository;

import com.es.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepository extends MongoRepository<Movie,String> {

    @Query("{'status' : true}")
    Page<Movie> findAllAndStatusTrue(Pageable pageable);

    List<Movie> searchByMovie(String searchText);

}
