package com.course.search.main.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.search.main.document.CourseDocument;
import com.course.search.main.dto.SearchRequestDTO;
import com.course.search.main.dto.SearchResponseDTO;
import com.course.search.main.mapper.CourseMapper;
import com.course.search.main.service.CourseSearchService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search")

public class CourseSearchController {

	private final ElasticsearchClient client;
	private final CourseSearchService service;
	
	public CourseSearchController(ElasticsearchClient client, CourseSearchService service) {
		this.client = client;
		this.service = service;
	}

    @GetMapping
    public SearchResponseDTO search(@ModelAttribute SearchRequestDTO request) throws Exception {

        var response = service.search(request);

        return SearchResponseDTO.builder()
                .total(response.hits().total().value())
                .courses(
                        response.hits().hits().stream()
                                .map(hit -> CourseMapper.toDto(hit.source()))
                                .toList()
                )
                .build();
	
    }
    
    
    
    @GetMapping("/suggest")
    public List<String> suggest(@RequestParam String q) throws Exception {
        return service.suggest(q);
    }
    
    
     
}
