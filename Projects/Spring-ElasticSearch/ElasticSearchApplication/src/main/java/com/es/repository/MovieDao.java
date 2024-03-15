package com.es.repository;

import com.es.model.Movie;
import org.bson.Document;

import java.util.List;

public interface MovieDao {
    List<Document> searchByContentAndText(Class<Movie> movieClass, String searchContent, String searchText);

    List<Document> searchByMultiContentAndText(Class<Movie> movieClass, String searchContent1, String searchText1, String searchContent2, String searchText2);
}
