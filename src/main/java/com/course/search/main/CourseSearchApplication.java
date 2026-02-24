package com.course.search.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(exclude = {
        org.springframework.boot.elasticsearch.autoconfigure.ElasticsearchClientAutoConfiguration.class
    })
@EnableElasticsearchRepositories(
        basePackages = "com.course.search.main.repository"
)
public class CourseSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseSearchApplication.class, args);
	}

}
