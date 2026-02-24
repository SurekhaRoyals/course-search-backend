package com.course.search.main.service;



import com.course.search.main.document.CourseDocument;
import com.course.search.main.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.List;


@Component
@RequiredArgsConstructor
public class CourseIndexService {
	
	private final CourseRepository repository;
//	 private final ElasticsearchClient client;
    private final ObjectMapper mapper;

    
    @PostConstruct
    public void indexCourses() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("sample-course.json")) {
            if (is == null) {
                throw new IllegalStateException("sample-course.json not found in classpath!");
            }

            List<CourseDocument> courses = mapper.readValue(is, new TypeReference<List<CourseDocument>>() {});
            repository.saveAll(courses);
            System.out.println("Indexed " + courses.size() + " courses into Elasticsearch");

        } catch (Exception e) {
            throw new IllegalStateException("Failed to index courses", e);
        }
    }
}