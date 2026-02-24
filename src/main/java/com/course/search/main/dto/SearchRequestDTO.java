package com.course.search.main.dto;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchRequestDTO {
	
	@Id
	public String q;

	public Integer minAge;
	public Integer maxAge;

    public String category;
    public String type;

    public Double minPrice;
    public Double maxPrice;

    public Instant startDate;

    // sorting: upcoming | priceAsc | priceDesc
    @Builder.Default
    public String sort = "upcoming";

    // pagination
    @Builder.Default
    public Integer page = 0;

    @Builder.Default
    public Integer size = 10;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
