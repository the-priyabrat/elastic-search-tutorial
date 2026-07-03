package com.project.elasticSearch.elasticservice;

import com.project.elasticSearch.dto.SearchResponse;

public interface ElasticSearchService {
    SearchResponse searchProduct(String keyWord);
}
