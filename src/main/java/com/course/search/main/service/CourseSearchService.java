package com.course.search.main.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.course.search.main.document.CourseDocument;
import com.course.search.main.dto.SearchRequestDTO;
//import com.course.search.main.repository.CourseRepository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class CourseSearchService {

    private final ElasticsearchClient client;

    public SearchResponse<CourseDocument> search(SearchRequestDTO request)
            throws IOException {

        BoolQuery.Builder bool = new BoolQuery.Builder();

       
        if (request.getQ() != null && !request.getQ().isBlank()) {
            bool.must(m -> m
                .multiMatch(mm -> mm
                    .query(request.getQ())
                    .fields("title", "description")
                    .type(TextQueryType.BoolPrefix)
                    .fuzziness("AUTO")
                )
            );
        }
        
       
        if (request.getCategory() != null) {
            bool.filter(f -> f
                .term(t -> t
                    .field("category.keyword")
                    .value(request.getCategory())
                )
            );
        }

        if (request.getType() != null) {
        	 bool.must(m -> m
        		        .multiMatch(mm -> mm
        		            .query(request.getType())
        		            .fields("title")
        		            .type(TextQueryType.BoolPrefix)   
        		        )
        		    );
        }
        
        if (request.getMinAge() != null || request.getMaxAge() != null) {

            if (request.getMaxAge() != null) {
                bool.filter(f -> f
                    .range(r -> r
                        .field("minAge")
                        .lte(JsonData.of(request.getMaxAge()))
                    )
                );
            }

            if (request.getMinAge() != null) {
                bool.filter(f -> f
                    .range(r -> r
                        .field("maxAge")
                        .gte(JsonData.of(request.getMinAge()))
                    )
                );
            }
        }

        if (request.getMinPrice() != null || request.getMaxPrice() != null) {
            bool.filter(f -> f
                .range(r -> {
                    r.field("price");
                    if (request.getMinPrice() != null) {
                        r.gte(JsonData.of(request.getMinPrice()));
                    }
                    if (request.getMaxPrice() != null) {
                        r.lte(JsonData.of(request.getMaxPrice()));
                    }
                    return r;
                })
            );
        }

        if (request.getStartDate() != null) {
            bool.filter(f -> f
                .range(r -> r
                    .field("nextSessionDate")
                    .gte(JsonData.of(request.getStartDate().toString()))
                )
            );
        }

        int from = request.getPage() * request.getSize();

        return client.search(s -> s
                .index("courses")
                .query(q -> q.bool(bool.build()))
                .from(from)
                .size(request.getSize())
                .sort(sort -> {
                    if ("priceAsc".equals(request.getSort())) {
                        sort.field(f -> f
                            .field("price")
                            .order(SortOrder.Asc)
                        );
                    } else if ("priceDesc".equals(request.getSort())) {
                        sort.field(f -> f
                            .field("price")
                            .order(SortOrder.Desc)
                        );
                    } else {
                        sort.field(f -> f
                            .field("nextSessionDate")
                            .order(SortOrder.Asc)
                        );
                    }
                    return sort;
                }),
                CourseDocument.class
        );
    }
    
    public List<String> suggest(String q) throws IOException {
        if (q == null || q.isBlank()) {
            return List.of();
        }

        var response = client.search(s -> s
                .index("courses")
                .query(qr -> qr
                    .multiMatch(mm -> mm
                        .query(q)
                        .fields("title")
                        .type(TextQueryType.BoolPrefix)
                    )
                )
                .size(10),
            CourseDocument.class
        );

        return response.hits().hits().stream()
                .map(hit -> hit.source().getTitle())
                .distinct()
                .toList();
    }
}
