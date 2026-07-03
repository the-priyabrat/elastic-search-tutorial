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
@Service("term")
public class ElasticTermSearchImplementation implements ElasticSearchService {

    Logger logger = LoggerFactory.getLogger(ElasticTermSearchImplementation.class);

    private final ElasticsearchOperations operations;

    /*{
        "query": {
        "term": {
            "status": {
                "value": "ACTIVE"
            }
        }
    }
    }

    Note:
    A term query is used for exact value matching. Unlike a match query, Elasticsearch does not analyze the search value. It is best suited for fields such as keyword, IDs, status codes, categories, or other fields where the value must match exactly.*/
    
    @Override
    public SearchResponse searchProduct(String keyWord) {
        try {
            Query query = NativeQuery.builder()
                    .withQuery(q -> q.term(t -> t.field("productId").value(keyWord)))
                    .build();
            SearchHits<ProductElasticEntity> hits = operations.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productList = hits.stream().map(SearchHit::getContent).toList();
            return SearchResponse.builder()
                    .message("Fetch Successful !")
                    .details(productList)
                    .build();
        } catch (Exception e) {
            logger.info("Failed due to {}", e.getMessage());
        }
        return SearchResponse.builder().message("Fetch failed !").details(List.of()).build();
    }
}
