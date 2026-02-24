package com.course.search.main.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseResponseDTO {

	private String id;
    private String title;
    private String category;
    private String type;
    private double price;
    private Instant nextSessionDate;


}
