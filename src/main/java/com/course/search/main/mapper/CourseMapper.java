package com.course.search.main.mapper;

import com.course.search.main.document.CourseDocument;
import com.course.search.main.dto.CourseResponseDTO;

public class CourseMapper {

	 public static CourseResponseDTO toDto(CourseDocument doc) {
	        return CourseResponseDTO.builder()
	                .id(doc.getId())
	                .title(doc.getTitle())
	                .category(doc.getCategory())
	                .type(doc.getType())
	                .price(doc.getPrice())
	                .nextSessionDate(doc.getNextSessionDate())
	                .build();
	    }
	
}
