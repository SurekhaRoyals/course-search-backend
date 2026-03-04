package com.course.search.main.config;

import org.springframework.context.annotation.Bean;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ElasticsearchConfig {


    @Bean(destroyMethod = "close")
    RestClient restClient() {
        return RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();
    }

    @Bean
    ElasticsearchClient elasticsearchClient(
            RestClient restClient,
            ObjectMapper objectMapper
    ) {
        ElasticsearchTransport transport =
                new RestClientTransport(
                        restClient,
                        new JacksonJsonpMapper(objectMapper)
                );

        return new ElasticsearchClient(transport);
    }
    
//    @Bean
//    ElasticsearchClient elasticsearchClient(RestClient restClient) {
//
//        ElasticsearchTransport transport =
//                new RestClientTransport(
//                        restClient,
//                        new JacksonJsonpMapper()
//                );
//
//        return new ElasticsearchClient(transport);
//    }
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
