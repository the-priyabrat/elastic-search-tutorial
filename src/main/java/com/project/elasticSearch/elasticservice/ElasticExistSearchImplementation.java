package com.project.elasticSearch.elasticservice;

import com.project.elasticSearch.entity.ProductElasticEntity;
import com.project.elasticSearch.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("exist")
public class ElasticExistSearchImplementation implements ElasticSearchService{

    private final ElasticsearchOperations operation;

    /*Elasticsearch Exists Query

    * Exists Query is used to retrieve documents where a specific field is present (exists).
    * It checks only whether a field has a value; it does **not** compare or search the field's content.
    * It is implemented using the `exists` query in Elasticsearch Query DSL.
    * In Spring Data Elasticsearch, it is built using `NativeQuery.builder().withQuery().exists()`.
    * The query requires only the `field` name; no search keyword or value is needed.
    * SQL Equivalent: `WHERE fieldName IS NOT NULL`.
    * Example: Searching for the field `productCategory` returns only documents that contain a `productCategory` value.
    * Documents where the specified field is missing or has a `null` value are not returned.
    * Common use cases include finding incomplete records, validating mandatory fields, filtering documents with optional fields, and checking data availability.

    *Difference:** `Term`, `Match`, and `Range` queries search field values, whereas an **Exists Query**
    only verifies whether the field exists in the document.*/


    @Override
    public SearchResponse searchProduct(String keyWord) {
        try{
            Query query = NativeQuery.builder()
                    .withQuery(q->q.exists(exist->exist.field("productName")))
                    .build();
            SearchHits<ProductElasticEntity> searchHits = operation.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productList = searchHits.stream().map(SearchHit::getContent).toList();
            return SearchResponse.builder()
                    .message("Fetched Successful !")
                    .details(productList)
                    .build();
        } catch(Exception e) {
            log.info("Failed due to {}", e.getMessage());
            return SearchResponse.builder()
                    .message("Fetch failed !")
                    .details(List.of())
                    .build();
        }
    }
}
