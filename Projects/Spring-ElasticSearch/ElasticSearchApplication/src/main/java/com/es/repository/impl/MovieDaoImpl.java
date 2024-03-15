package com.es.repository.impl;

import com.es.model.Movie;
import com.es.repository.MovieDao;
import com.es.util.BaseMethods;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieDaoImpl implements MovieDao {

    private final MongoTemplate mongoTemplate;

//    private final MongoClient mongoClient;
//    private final String database;

    public MovieDaoImpl(MongoTemplate mongoTemplate, MongoClient mongoClient, String database) {
        this.mongoTemplate = mongoTemplate;

    }

    @Override
    public List<Document> searchByContentAndText(Class<Movie> voName, String searchContent, String searchText) {

        System.out.println("SearchContent : " + searchContent + "|| SearchText : " + searchText);

        if (!searchContent.equalsIgnoreCase("releaseYear")) {
            searchText = BaseMethods.getStringWithQuotes(searchText);
        }

        searchContent = BaseMethods.getStringWithQuotes(searchContent);


        MongoDatabase database = mongoTemplate.getDb();
        System.out.println("Database Name : " + database.getName());


        MongoCollection<Document> collection = database.getCollection("movie");


        String jsonQuery = " { " + searchContent + " : " + searchText + " } ";


        System.out.println("Json Query : " + jsonQuery);

        Document doc = Document.parse(jsonQuery);
        System.out.println("JSON DOC : " + doc.toJson());
        FindIterable<Document> document = collection.find(doc);
        List<Document> results = new ArrayList<>();
        for (Document doc1 : document) {
            System.out.println(doc1.toJson());
            results.add(doc1);
        }

        System.out.println(results);

        return results;

//        try {
//            Query query = new Query(Criteria.where(searchContent).regex("^" + searchText,"i").and("status").is(true));
//            return mongoTemplate.find(query, voName);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }

    }


    @Override
    public List<Document> searchByMultiContentAndText(Class<Movie> movieClass, String searchContent1, String searchText1, String searchContent2, String searchText2) {

        StringBuilder sb = null;

        if (!searchContent1.equalsIgnoreCase("releaseYear")) {
            System.out.println(" IF ->>> 1 ");
            searchText1 = BaseMethods.getStringWithQuotes(searchText1);
            searchContent1 = BaseMethods.getStringWithQuotes(searchContent1);
            sb = BaseMethods.getJsonString(searchContent1, searchText1);
        } else {
            System.out.println(" Else ->>> 1 ");
            Integer searchIntText1 = Integer.parseInt(searchText1);
            searchContent1 = BaseMethods.getStringWithQuotes(searchContent1);
            sb = BaseMethods.getJsonStringWithIntValue(searchContent1, searchIntText1);
        }


        if (BaseMethods.validateStrNotNullOrNotEmpty(searchContent2) && BaseMethods.validateStrNotNullOrNotEmpty(searchText2) && !searchContent2.equalsIgnoreCase("releaseYear")) {
            System.out.println(" IF ->>> 2 ");
            searchText2 = BaseMethods.getStringWithQuotes(searchText2);
            searchContent2 = BaseMethods.getStringWithQuotes(searchContent2);
            sb.append(BaseMethods.getJsonString(searchContent2, searchText2));
        } else if (BaseMethods.validateStrNotNullOrNotEmpty(searchContent2) && BaseMethods.validateStrNotNullOrNotEmpty(searchText2)) {
            System.out.println(" Else ->>> 2 ");
            Integer searchIntText2 = Integer.parseInt(searchText2);
            searchContent2 = BaseMethods.getStringWithQuotes(searchContent2);
            sb.append(BaseMethods.getJsonStringWithIntValue(searchContent2, searchIntText2));
        }

        System.out.println("Json Object : " + sb);

        MongoDatabase database = mongoTemplate.getDb();
        System.out.println("Database Name : " + database.getName());


        MongoCollection<Document> collection = database.getCollection("movie");
        String jsonQuery = "";
        if (BaseMethods.validateStrNotNullOrNotEmpty(searchContent1)) {
            jsonQuery = "{ $or: [ " + sb + " ]}";
        }

        System.out.println("Json Query : " + jsonQuery);

        Document doc = Document.parse(jsonQuery);
        System.out.println("JSON DOC : " + doc.toJson());
        FindIterable<Document> document = collection.find(doc);

        List<Document> results = new ArrayList<>();
        for (Document doc1 : document) {
            System.out.println(doc1.toJson());
            results.add(doc1);
        }

        System.out.println(results);

        return results;
    }

}

