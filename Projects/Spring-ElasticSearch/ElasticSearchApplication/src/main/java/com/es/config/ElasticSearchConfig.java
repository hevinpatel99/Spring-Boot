package com.es.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.stats.ShardCommit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.CanMatchNodeRequest;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {


//------------------------ Deprecated RestHighLevelClient -----------------------

//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//
//        final ClientConfiguration clientConfiguration =
//                ClientConfiguration
//                        .builder()
//                        .connectedTo("localhost:9200")
//                        .build();
//
//        return RestClients.create(clientConfiguration).rest();
//    }

//------------------------ Deprecated -----------------------


    // To configure the URL and port on which Elasticsearch is running.
    @Bean
    public RestClient getRestClient() {
        return RestClient
                .builder(new HttpHost("localhost", 9200))
                .build();
    }


    // Returns the Transport Object, whose purpose is it automatically map our Model Class to JSON and integrates them with API Client.
    @Bean
    public ElasticsearchTransport getElasticsearchTransport() {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
    }

    // Returns a bean of ElasticsearchClient, which we further use to perform all query operation with Elasticsearch.
    @Bean
    public ElasticsearchClient getElasticsearchClient() {
        return new ElasticsearchClient(getElasticsearchTransport());
    }


}
