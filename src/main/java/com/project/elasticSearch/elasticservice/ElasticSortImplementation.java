package com.project.elasticSearch.elasticservice;

import co.elastic.clients.elasticsearch._types.SortOrder;
import com.project.elasticSearch.entity.ProductElasticEntity;
import com.project.elasticSearch.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service("sort")
public class ElasticSortImplementation implements ElasticSortingAndPaginationService{

    private final ElasticsearchOperations operations;


    /*Elasticsearch Sorting & Pagination

    Sorting is used to arrange search results in a specific order based on a document field. It is implemented using `withSort(
    ` in `NativeQuery`. The sorting field can be numeric, date, or keyword. `SortOrder.Asc` returns results in ascending order
    (lowest to highest or A–Z), while `SortOrder.Desc` returns results in descending order (highest to lowest or Z–A). Numeric
    fields such as `Double`, `Integer`, or `Long` are recommended for numeric sorting, whereas exact string sorting should be
    performed on `keyword` fields. Pagination is implemented using `withPageable(PageRequest.of(pageNo, pageSize))`, where
    `pageNo` is the zero-based page index and `pageSize` is the number of documents returned per page. For example, `PageRequest
    .of(0, 10)` returns the first 10 documents, while `PageRequest.of(1, 10)` returns documents 11–20. Elasticsearch returns
    only **10 documents by default**, so pagination should always be used when retrieving large result sets. Sorting and
    pagination can be combined in a single query to efficiently return ordered and paginated search results, making them
    essential features in production-grade search applications.*/


    @Override
    public SearchResponse sortProduct(int pageNumber, int pageSize) {
        try{
            Query query = NativeQuery.builder()
                    .withSort(sort->sort.field(field->field.field("productPrice").order(SortOrder.Asc))).withPageable(PageRequest.of(pageNumber, pageSize))
                    .build();
            SearchHits<ProductElasticEntity> searchHits = operations.search(query, ProductElasticEntity.class);
            List<ProductElasticEntity> productList = searchHits.stream().map(SearchHit::getContent).toList();
            return SearchResponse.builder()
                    .message("Fetched Successfully !")
                    .details(productList)
                    .build();
        } catch(Exception e) {
            log.info("Failed for {}", e.getMessage());
            return SearchResponse.builder().message(e.getMessage()).build();
        }
    }
}
