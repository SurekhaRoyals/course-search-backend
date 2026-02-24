package com.course.search.main.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.course.search.main.document.CourseDocument;

//@Repository
public interface CourseRepository extends ElasticsearchRepository<CourseDocument, String>{
}
