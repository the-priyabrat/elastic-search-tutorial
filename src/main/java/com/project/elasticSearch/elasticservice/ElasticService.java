package com.project.elasticSearch.elasticservice;

import com.project.elasticSearch.entity.ProductElasticEntity;

import java.util.List;

public interface ElasticService {
    ProductElasticEntity findById(String id);

    ProductElasticEntity saveEntity(ProductElasticEntity elasticEntity);

    List<String> saveBulk(List<ProductElasticEntity> products);

    List<ProductElasticEntity> getAll();
}
