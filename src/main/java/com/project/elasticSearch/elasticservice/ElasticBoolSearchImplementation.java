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

import java.util.List;

@RequiredArgsConstructor
@Service("bool")
public class ElasticBoolSearchImplementation implements ElasticSearchService {

    Logger logger = LoggerFactory.getLogger(ElasticBoolSearchImplementation.class);

    private final ElasticsearchOperations operation;

 /*   {
        "query":{
        "bool":{
            "must": [
            {
                "match":{
                "productName":"mouse"
            }
            },
            {
                "term":{
                "productCategory":"Electronics"
            }
            }
            ]
        }
    }
    } to match the query for bool search
    Note:
    A bool query with should is conceptually similar to the OR operator in SQL. It returns documents that satisfy one or more of the specified conditions. In this example, a document matches if its productId is exactly "P1001" OR its productName matches "Wireless Mouse" through full-text search.

    SELECT *
    FROM products
    WHERE product_id = 'P1001'
    OR product_name LIKE '%Wireless Mouse%';
    Difference: SQL LIKE performs pattern matching, whereas Elasticsearch match performs full-text search using analyzers (tokenization, lowercasing, stemming, etc.), making it much more powerful for searching natural language text.
    */

    @Override
    public SearchResponse searchProduct(String keyWord) {
        try {
            Query query = NativeQuery.builder()
                    .withQuery(q -> q
                            .bool(bool -> bool
                                    .should(should -> should
                                            .term(term -> term
                                                    .field("productId").value(keyWord)))
                                    .should(should -> should
                                            .match(match -> match.field("productName").query(keyWord)))))
                    .build();
            SearchHits<ProductElasticEntity> searchHits = operation.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productList =
                    searchHits.stream().map(SearchHit::getContent).toList();
            return SearchResponse.builder()
                    .details(productList)
                    .message("Fetched Successfully")
                    .build();
        } catch (Exception e) {
            logger.info("Failed for message: {}", e.getMessage());
        }
        return SearchResponse.builder()
                .details(List.of())
                .message("Fetch failed").build();
    }
}
