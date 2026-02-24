package com.course.search.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.search.main.dto.SearchRequestDTO;
import com.course.search.main.dto.SearchResponseDTO;
import com.course.search.main.mapper.CourseMapper;
import com.course.search.main.service.CourseSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search")

public class CourseSearchController {

	private final CourseSearchService service;
	
	public CourseSearchController(CourseSearchService service) {
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
	
	
}
