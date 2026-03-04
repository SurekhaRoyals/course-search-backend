package com.course.search.main.service;



import com.course.search.main.document.CourseDocument;
import com.course.search.main.repository.CourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;


@Component
@RequiredArgsConstructor
public class CourseIndexService {
	
	private final CourseRepository repository;
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
















//  BulkRequest.Builder bulkBuilder = new BulkRequest.Builder();
//
//for (CourseDocument course : courses) {
//    bulkBuilder.operations(oper -> oper
//        .index(idx -> idx
//            .index("courses")
//            .id(course.getId())   
//            .document(course)
//        )
//    );
//}
//
//client.bulk(bulkBuilder.build());
//
//} catch (Exception e) {
//throw new IllegalStateException("Failed to index courses", e);
//}