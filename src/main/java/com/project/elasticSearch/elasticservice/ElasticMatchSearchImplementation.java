package com.project.elasticSearch.elasticservice;

import com.project.elasticSearch.entity.ProductElasticEntity;
import com.project.elasticSearch.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service("match")
public class ElasticMatchSearchImplementation implements ElasticSearchService {

    Logger logger = LoggerFactory.getLogger(ElasticMatchSearchImplementation.class);

    private final ElasticsearchOperations operations;


    /*{
        "query": {
        "match": {
            "description": {
                "query": "wireless bluetooth headphones"
            }
        }
    }
    }

    Note:
    A match query is used for full-text search on analyzed (text) fields. Elasticsearch analyzes both the search text and the indexed field (e.g., tokenization, lowercasing) before matching, making it suitable for searching natural language content rather than exact values. */

    @Override
    public SearchResponse searchProduct(String keyWord) {
        try {
            Query query = NativeQuery.builder()
                    .withQuery(
                            q -> q.match(
                                    m -> m.field("productName")
                                            .query(keyWord)))
                    .build();
            SearchHits<ProductElasticEntity> hits = operations.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productLists = new ArrayList<>();
            productLists = hits.stream()
                    .map(SearchHit::getContent)
                    .toList();
            return SearchResponse.builder()
                    .message("Fethced SuccessFully")
                    .details(productLists)
                    .build();
        } catch (Exception e) {
            logger.info("Failed at {}", e.getMessage());
        }
        return SearchResponse.builder().message("Failed to fetch !").details(List.of()).build();
    }
}


