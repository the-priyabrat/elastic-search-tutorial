package com.project.elasticSearch.elasticservice;

import com.project.elasticSearch.dto.SearchResponse;

public interface ElasticSortingAndPaginationService {
    SearchResponse sortProduct(int start, int size);
}
