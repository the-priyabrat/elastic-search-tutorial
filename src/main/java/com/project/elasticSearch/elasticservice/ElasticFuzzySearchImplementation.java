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
@Service("fuzzy")
public class ElasticFuzzySearchImplementation implements ElasticSearchService{

    private final ElasticsearchOperations operation;

    @Override
    public SearchResponse searchProduct(String keyWord) {
        try{
            Query query = NativeQuery.builder()
                    .withQuery(q->q.fuzzy(fuzz->fuzz.field("productName")
                            .value(keyWord).fuzziness("AUTO")))
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
