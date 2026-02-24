package com.course.search.main.config;

import org.springframework.context.annotation.Bean;

//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.client.elc.RestClientConfiguration;
//import org.springframework.data.elasticsearch.client.elc.RestClients;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ElasticsearchConfig {
	
	

	
	@Bean(destroyMethod = "close")
    public RestClient restClient() {
        return RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(
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
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	    @Bean
//	    public ObjectMapper objectMapper() {
//	        ObjectMapper mapper = new ObjectMapper();
//	        mapper.registerModule(new JavaTimeModule()); // for LocalDate, LocalDateTime
//	        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO strings
//	        return mapper;
//	    }
////
////	   
//	    @Bean
//	    public ElasticsearchClient elasticsearchClient() {
//	        RestClient restClient = RestClient.builder(
//	                new HttpHost("localhost", 9200, "http")
//	        ).build();
//
//	        RestClientTransport transport = new RestClientTransport(
//	                restClient,
//	                new JacksonJsonpMapper()
//	        );
//
//	        return new ElasticsearchClient(transport);
//	    }

//}

// ---- 2. Low-level Elasticsearch RestClient ----
//@Bean
//public RestClient restClient() {
//    return RestClient.builder(new HttpHost("localhost", 9200)).build();
//}
//
//// ---- 3. ElasticsearchTransport using our ObjectMapper ----
//@Bean
//public ElasticsearchTransport elasticsearchTransport(ObjectMapper objectMapper) {
//    return new RestClientTransport(restClient(), new JacksonJsonpMapper(objectMapper));
//}
//
//// ---- 4. ElasticsearchClient bean ----
//@Bean
//public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
//    return new ElasticsearchClient(transport);
//}
//

//@Bean
//public RestClient getRestClient() {
//RestClient restClient = RestClient.builder(
//        new HttpHost("localhost", 9200)).build();
//return restClient;
//}
//
//@Bean
//public  ElasticsearchTransport getElasticsearchTransport() {
//return new RestClientTransport(
//        getRestClient(), new JacksonJsonpMapper());
//}
//
//
//@Bean
//public ElasticsearchClient getElasticsearchClient(){
//ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
//return client;
//}


