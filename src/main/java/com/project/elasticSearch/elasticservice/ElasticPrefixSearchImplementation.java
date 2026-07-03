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
@Service("prefix")
public class ElasticPrefixSearchImplementation implements ElasticSearchService{

    private final ElasticsearchOperations operations;

    /*## Elasticsearch Prefix Query

    * **Prefix Query** is used to retrieve documents whose field value **starts with a specified prefix**.
    * It performs **prefix matching**, meaning only values beginning with the given text are returned.
    * It is implemented using the `prefix` query in Elasticsearch Query DSL.
    * In Spring Data Elasticsearch, it is built using `NativeQuery.builder().withQuery().prefix()`.
    * The `field` parameter specifies the field to search, and the `value` parameter specifies the prefix.
    * SQL Equivalent: `WHERE productName LIKE 'Lap%'`.
    * Example: Searching `"Lap"` matches **Laptop**, **Laptop Stand**, and **Laptop Bag**.
    * Unlike a **Match Query**, a Prefix Query does not perform full-text relevance search; it checks whether the indexed term starts with the given prefix.
    * It is faster than a **Wildcard Query** because Elasticsearch only searches terms that begin with the specified prefix.
    * Prefix queries work best on **Keyword** fields for exact prefix matching. On **Text** fields, they operate on analyzed tokens rather than the original string.
    * Common use cases include search suggestions, product code lookup, employee ID search, customer ID search, and basic autocomplete functionality.
    * Difference:** Match Query searches analyzed text, Fuzzy Query tolerates spelling mistakes, whereas Prefix Query searches only values that start with the given prefix.*/



    @Override
    public SearchResponse searchProduct(String keyWord) {
        try{
            Query query = NativeQuery.builder()
                    .withQuery(q->q
                            .bool(bool->bool
                                    .should(should->should
                                            .prefix(pre->pre
                                                    .field("productName").value(keyWord)))
                                    .should(should->should
                                            .prefix(pre->pre
                                                    .field("productCategory").value(keyWord)))))
                    .build();
            SearchHits<ProductElasticEntity> searchHits = operations.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productList = searchHits.stream().map(SearchHit::getContent).toList();
            return SearchResponse.builder()
                    .details(productList)
                    .message("Fetched Successfully !")
                    .build();
        } catch(Exception e){
            log.info("Failed for {}", e.getMessage());
            return SearchResponse.builder()
                    .details(List.of())
                    .message("Fetched Successfully !")
                    .build();
        }
    }
}
