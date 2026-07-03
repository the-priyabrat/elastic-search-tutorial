package com.project.elasticSearch.elasticservice;

import com.project.elasticSearch.dto.SearchResponse;

public interface ElasticWithinRangeSearch {
    SearchResponse searchWithinRanges(Double first, Double last);
}
