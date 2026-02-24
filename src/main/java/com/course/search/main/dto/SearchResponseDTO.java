package com.course.search.main.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchResponseDTO {
	private long total;
    private List<CourseResponseDTO> courses;
}
