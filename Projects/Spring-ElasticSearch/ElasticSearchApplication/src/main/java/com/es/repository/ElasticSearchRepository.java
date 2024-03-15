package com.es.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.es.model.Movie;
import com.es.service.serviceimpl.ElasticSearchServiceImpl;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Repository
public class ElasticSearchRepository<B, S> {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchRepository.class);

    private final ElasticsearchClient elasticsearchClient;


    private final String indexName = "movie";

    public ElasticSearchRepository(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;

    }

    public String saveDataInElastic(Movie movie) throws IOException {
        logger.info("ES MOVIE JSON : " + new Gson().toJson(movie));
        IndexResponse insertResponse = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(movie.getId())
                .document(movie)
        );

        if (insertResponse.result().name().equals("Created")) {
            return "Insert Data In ElasticSearch Successfully.";
        }


//        IndexRequest<?> indexRequest = new IndexRequest(indexName).id(movie.getId());
//        indexRequest.source(new Gson().toJson(movie), XContentType.JSON);
//        restClient.index(indexRequest, RequestOptions.DEFAULT);

        return "Not Insert Data In ElasticSearch.";
    }


    public List<Movie> getElasticData() {
        logger.info("--- getElasticData ---");

        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
            SearchResponse<Movie> searchResponse = elasticsearchClient.search(searchRequest, Movie.class);
            if (searchResponse != null) {
                List<Hit<Movie>> hits = searchResponse.hits().hits();
                List<Movie> movieList = new ArrayList<>();
                for (Hit<Movie> object : hits) {
                    System.out.print(new Gson().toJson(object.source()));
                    movieList.add(object.source());
                }
                return movieList;
            }

        } catch (Exception e) {
            System.out.println(" Exception " + e.getMessage());
        }

//        try {
//            SearchRequest searchRequest = new SearchRequest(indexName);
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            MatchAllQueryBuilder queryBuilder = matchAllQuery();
//            searchSourceBuilder.query(queryBuilder);
//            searchRequest.source(searchSourceBuilder);
//            return ResponseEntity.ok(restClient.search(searchRequest, RequestOptions.DEFAULT));
//
//        } catch (Exception e) {
//            System.out.println(" Exception " + e.getMessage());
//        }

        return Collections.emptyList();
    }

    public List<Movie> getElasticDataBySearch(String searchContent, String searchText) {

        logger.info("--- getElasticDataBySearch ---");

        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName)
                    .query(q -> q.match(t -> t.field(searchContent).query(searchText))));
            SearchResponse<Movie> searchResponse = elasticsearchClient.search(searchRequest, Movie.class);
            if (searchResponse != null) {
                List<Hit<Movie>> hits = searchResponse.hits().hits();
                List<Movie> movieList = new ArrayList<>();
                for (Hit<Movie> object : hits) {
                    System.out.print(new Gson().toJson(object.source()));
                    movieList.add(object.source());
                }
                return movieList;
            }
        } catch (Exception e) {
            System.out.println(" Exception " + e.getMessage());
        }

//        try {
//            SearchRequest searchRequest = new SearchRequest(indexName);
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            QueryBuilder queryBuilder = null;
//            if (searchContent.equalsIgnoreCase("actor")) {
//                queryBuilder = QueryBuilders.matchQuery("actor", searchText);
//            } else if (searchContent.equalsIgnoreCase("actress")) {
//                queryBuilder = QueryBuilders.matchQuery("actress", searchText);
//            } else if (searchContent.equalsIgnoreCase("movie")) {
//                queryBuilder = QueryBuilders.matchQuery("movie", searchText);
//            } else if (searchContent.equalsIgnoreCase("releaseYear")) {
//                queryBuilder = QueryBuilders.matchQuery("releaseYear", Integer.valueOf(searchText));
//            } else {
//                System.out.println("Not found contain !!");
//            }
//            searchSourceBuilder.query(queryBuilder);
//            searchRequest.source(searchSourceBuilder);
//            return ResponseEntity.ok(restClient.search(searchRequest, RequestOptions.DEFAULT));
//
//        } catch (Exception e) {
//            System.out.println(" Exception " + e.getMessage());
//        }

        return Collections.emptyList();
    }

    public List<Movie> getElasticDataByKeyWord(String searchValue) {

        logger.info("--- getElasticDataByKeyWord --- SearchValue : " + searchValue);


        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName)
                    .from(0).size(25)
                    .query(q -> q.multiMatch(
                            t -> t.fields(String.valueOf(searchValue), "movie", "actor", "actress")
                                    .query(String.valueOf(searchValue))
                    )));
            SearchResponse<Movie> searchResponse = elasticsearchClient.search(searchRequest, Movie.class);
            if (searchResponse != null) {
                List<Hit<Movie>> hits = searchResponse.hits().hits();
                List<Movie> movieList = new ArrayList<>();
                for (Hit<Movie> object : hits) {
                    System.out.print(new Gson().toJson(object.source()));
                    movieList.add(object.source());
                }
                return movieList;
            }
        } catch (Exception e) {
            System.out.println(" Exception " + e.getMessage());
        }
//        try {
//            SearchRequest searchRequest = new SearchRequest(indexName);
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            QueryBuilder queryBuilder = (QueryBuilder) new NativeSearchQueryBuilder()
//                    .withFilter(QueryBuilders.multiMatchQuery(keyWord, "movie", "actor", "actress", "releaseYear").fuzziness(Fuzziness.AUTO))
//                    .build();
//            searchSourceBuilder.query(queryBuilder);
//            searchRequest.source(searchSourceBuilder);
//            return ResponseEntity.ok(restClient.search(searchRequest, RequestOptions.DEFAULT));
//        } catch (Exception e) {
//            System.out.println(" Exception " + e.getMessage());
//        }
        return Collections.emptyList();
    }

    public String removeData(Movie movie) throws IOException {

        logger.info("--- removeData ---");
        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(movie.getId()));
        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (deleteResponse.result().name().equals("NotFound")) {
            return "Remove Record In ElasticSearch Successfully.";
        }
        return null;
    }

//        DeleteRequest deleteRequest = new DeleteRequest(indexName).id(movie.getId());
//        restClient.delete(deleteRequest, RequestOptions.DEFAULT);


    public void updateDataInES(Movie movie) throws IOException {

        logger.info("--- updateDataInES ---");

        GetResponse<Movie> response = elasticsearchClient.get(g -> g
                .index(indexName)
                .id(movie.getId()), Movie.class);

        if (response.found()) {
            elasticsearchClient.update(g -> g
                    .index(indexName)
                    .id(movie.getId()).doc(movie).docAsUpsert(true), Movie.class);
        } else {
            saveDataInElastic(movie);
        }

//        SearchResponse<Movie> searchResponse = elasticsearchClient.search(searchRequest, Movie.class);
//        if (searchResponse != null) {
//            List<Hit<Movie>> hits = searchResponse.hits().hits();
//            List<Movie> movieList = new ArrayList<>();
//            for (Hit<Movie> object : hits) {
//                System.out.print((object.source()));
//                movieList.add(object.source());
//            }
//            return movieList;
//        }

//        IndexRequest indexRequest = new IndexRequest(indexName);
//        SearchResponse searchResponse = getElasticDataBySearch("id", movie.getId()).getBody();
//        if (searchResponse != null && searchResponse.getHits() != null
//                && searchResponse.getHits().getHits() != null && searchResponse.getHits().getHits().length > 0) {
//            indexRequest.source(new Gson().toJson(movie), XContentType.JSON);
//            restClient.update(new UpdateRequest(indexName, movie.getId()).doc(indexRequest).docAsUpsert(true), RequestOptions.DEFAULT);
//        }
    }

    public void deleteElasticSearchData(String id) throws IOException {
        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(id));
        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (deleteResponse.result().name().equals("NotFound")) {
            logger.info("Remove Record In ElasticSearch Successfully.");
        }
    }
}

