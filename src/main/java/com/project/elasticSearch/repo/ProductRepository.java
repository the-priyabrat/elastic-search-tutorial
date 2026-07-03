package com.project.elasticSearch.repo;

import com.project.elasticSearch.entity.ProductElasticEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductElasticEntity, String> {
}
