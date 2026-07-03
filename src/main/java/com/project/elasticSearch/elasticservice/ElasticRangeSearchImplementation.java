package com.project.elasticSearch.elasticservice;

import com.project.elasticSearch.entity.ProductElasticEntity;
import com.project.elasticSearch.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service("range")
public class ElasticRangeSearchImplementation implements ElasticSearchService, ElasticWithinRangeSearch {

    Logger logger = LoggerFactory.getLogger(ElasticBoolSearchImplementation.class);
    private final ElasticsearchOperations operation;

    /*
    Understanding the Query
    range: Specifies that Elasticsearch should perform a range search.
    price: The field on which the range condition is applied.
    gte : Includes documents whose price is 1000 or more (Greater Than or Equal To).
    lte : Includes documents whose price is 5000 or less (Less Than or Equal To).
    {
        "query": {
        "range": {
            "price": {
                "gte": 1000,
                        "lte": 5000
                }
            }
         }
      }
    The above query returns all products whose price is between 1000 and 5000 (inclusive).
    Key Points
    Works on numeric, date, and other comparable field types.
    Returns documents whose field values fall within the specified boundaries.
    Supports inclusive (gte, lte) and exclusive (gt, lt) comparisons.
    Similar to SQL's BETWEEN, >=, <=, >, and < operators.
    following is the example of greater than for elastic range
    Query query = NativeQuery.builder()
         .withQuery(qu->qu.range(range->range.number(num->num
         .field("productPrice").lte(price))))
         .withPageable(PageRequest.of(0,20))
         .build();*/
    @Override
    public SearchResponse searchProduct(String keyWord) {
        try {
            Double price = Double.parseDouble(keyWord);
            Query query = NativeQuery.builder()
                    .withQuery(qu -> qu.range(range -> range.number(num -> num.field("productPrice").gt(price)))).withPageable(PageRequest.of(0, 20))
                    .build();
            SearchHits<ProductElasticEntity> searchHits = operation.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productList = searchHits.stream().map(SearchHit::getContent).toList();
            return SearchResponse.builder().message("Fetched Successfully").details(productList).build();
        } catch (Exception e) {
            logger.info("Failed with message {}", e.getMessage());
            return SearchResponse.builder().message(e.getMessage()).build();
        }
    }

    /*## Elasticsearch Range Query

    A Range Query is used to retrieve documents whose numeric or date field values fall within a specified range. It supports the operators `gt` (greater than), `gte` (greater than or equal), `lt` (less than), and `lte` (less than or equal). For a single field, the recommended approach is to use one `range` query containing both bounds, for example `gte(first)` and `lte(last)`, which is equivalent to the SQL statement `WHERE productPrice >= first AND productPrice <= last`. A `bool` query with multiple `must` clauses can also achieve the same result by combining separate range queries, but it is mainly preferred when multiple fields or different query types are involved. Range queries should always be used on numeric or date fields (e.g., `Double`, `Integer`, `Long`, `Date`) rather than `String` fields.
     elastic feature for range operation
            Query query = NativeQuery.builder()
                    .withQuery(q -> q.range(r -> r.number(n -> n.field("productPrice")
                    .gte(first)
                    .lte(last))))
                    .build();
      You can try the upper logic provided by elastic search*/

    @Override
    public SearchResponse searchWithinRanges(Double first, Double last) {
        try {
            Query query = NativeQuery.builder()
                    .withQuery(q -> q
                            .bool(bool -> bool
                                    .must(must -> must
                                            .range(range -> range
                                                    .number(num -> num
                                                            .field("productPrice")
                                                            .gte(first)))).must(must -> must
                                            .range(range -> range
                                                    .number(num -> num
                                                            .field("productPrice")
                                                            .lte(last))))))
                    .build();
            SearchHits<ProductElasticEntity> searchHits = operation.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productList = searchHits.stream().map(SearchHit::getContent).toList();
            return SearchResponse.builder()
                    .details(productList)
                    .message("Fetched Successfully !")
                    .build();
        } catch (Exception e) {
            logger.info("Failed for {}", e.getMessage());
            return SearchResponse.builder().details(List.of()).message("Fetch Failed !").build();
        }
    }
}
