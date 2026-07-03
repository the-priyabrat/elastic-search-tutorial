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
@Service("multi_fuzzy")
public class ElasticMultiMatchFuzzyImplementation implements ElasticSearchService{

    private final ElasticsearchOperations operations;


    /*Multi Match Query with Fuzziness
    A Multi Match Query is used to search for the same search text across multiple fields in a document. Instead of
    searching a single field, Elasticsearch checks all the specified fields and returns documents that match any of
    them.

    By adding fuzziness: "AUTO", Elasticsearch becomes tolerant of minor spelling mistakes and typing errors. It
    automatically determines how many character changes are allowed based on the length of the search term, making
    the search more user-friendly.

    For example, if a document contains "Wireless Headphones" and the user searches for "wirless hedphones",
    Elasticsearch can still return the correct document because fuzziness: "AUTO" recognizes the small spelling
    differences.

    This is especially useful in applications where users may mistype product names, brands, or descriptions, such as
    e-commerce websites, search engines, and inventory management systems.*/

    @Override
    public SearchResponse searchProduct(String keyWord) {
        try{
            Query query = NativeQuery.builder()
                    .withQuery(q-> q
                            .multiMatch(match-> match
                                    .fields("productName","productCategory")
                                    .query(keyWord).fuzziness("AUTO")))
                    .build();
            SearchHits<ProductElasticEntity> searchHits = operations.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productList = searchHits.stream().map(SearchHit::getContent).toList();
            return SearchResponse.builder()
                    .details(productList)
                    .message("Fetched successfully !")
                    .build();
        }catch (Exception e){
            log.info("Failed for {}", e.getMessage());
            return SearchResponse.builder()
                    .details(List.of())
                    .message("Fetch failed !")
                    .build();
        }
    }
}
