package com.project.elasticSearch.dto;

import com.project.elasticSearch.entity.ProductElasticEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class SearchResponse {
    private String message;
    private List<ProductElasticEntity> details;
}
