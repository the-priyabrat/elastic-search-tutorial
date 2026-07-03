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

@RequiredArgsConstructor
@Slf4j
@Service("multi")
public class ElasticMultiMatchImplementation implements ElasticSearchService{

    private final ElasticsearchOperations operation;

    /* Elasticsearch Multi Match Query
            * **Multi Match Query** is used to search a **single keyword across multiple fields** in a document.
            * It is an extension of the **Match Query**, where instead of one field, multiple fields are searched simultaneously.
            * It is ideal for implementing a **global search bar** in applications.
            * It uses the `multi_match` query in Elasticsearch Query DSL.
            * In Spring Data Elasticsearch, it is implemented using `NativeQuery.builder().withQuery().multiMatch()`.
            * The `query` parameter specifies the keyword to search.
            * The `fields` parameter contains the list of fields to search (e.g., `productName`, `productCategory`, `productDescription`).
            * The query returns documents where **any of the specified fields** match the search keyword.
            * Field boosting can be applied using the `^` operator (e.g., `productName^3`) to give higher relevance to important fields.
            * The search score (`_score`) determines the order of matching documents based on relevance.
            * It supports full-text search features such as tokenization, analyzers, and stemming, just like the Match Query.
            * It should be used only with **Text** fields; for exact matching on **Keyword** fields, use a **Term Query**.
            * SQL Equivalent: `WHERE productName LIKE '%keyword%' OR productCategory LIKE '%keyword%' OR productDescription LIKE '%keyword%'`.
            * Common use cases include product search, employee search, movie search, blog search, and document search across multiple attributes.
            * **Difference:** `Match Query` searches one field, whereas `Multi Match Query` searches multiple fields using a single query. */

    @Override
    public SearchResponse searchProduct(String keyWord) {
        try{
            Query query = NativeQuery.builder()
                    .withQuery(q->q
                            .multiMatch(multi->multi
                                    .fields("productId",
                                            "productName",
                                            "productDescription^3",
                                            "productCategory").query(keyWord)))
                    .build();
            SearchHits<ProductElasticEntity> searchHits = operation.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productList = searchHits.stream().map(SearchHit::getContent).toList();
            return SearchResponse.builder()
                    .details(productList)
                    .message("Successfully Fetched !")
                    .build();
        } catch(Exception e) {
            log.info(e.getMessage());
        }
        return SearchResponse.builder().details(List.of()).message("Fetch failed !").build();
    }
}
