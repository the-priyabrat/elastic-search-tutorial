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
@Service("wildcard")
public class ElasticWildCardSearchImplementation implements ElasticSearchService {
    private final ElasticsearchOperations operation;

    /*Wildcard Query

    A Wildcard Query is used to search for documents by matching a pattern instead of an exact value. It uses wildcard
    characters to represent unknown characters, making it useful for partial or pattern-based searches.* → Matches zero
    or more characters.
    ? → Matches exactly one character.

    For example, the pattern Sam* matches Samsung, Sample, and Samson, while Ph?ne matches Phone but not Phonne.

    Wildcard queries are best suited for keyword fields because they do not analyze the search text. They are useful
    for searching codes, IDs, filenames, or other structured values.Note: Wildcard queries can be slower than match or
    term queries, especially when the pattern starts with * (for example, *phone), because Elasticsearch must scan more
    terms. Use them only when pattern matching is required.*/

    @Override
    public SearchResponse searchProduct(String keyWord) {
        try{
            Query query = NativeQuery.builder()
                    .withQuery(q->q
                            .wildcard(wild->wild
                                    .field("productName").value("*"+keyWord+"*")))
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
