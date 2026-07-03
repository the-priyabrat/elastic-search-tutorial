package com.project.elasticSearch.elasticservice;

import com.project.elasticSearch.entity.ProductElasticEntity;
import com.project.elasticSearch.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ElasticImplementation implements ElasticService{

    Logger logger = LoggerFactory.getLogger(ElasticImplementation.class);

    private final ProductRepository productRepo;

    @Override
    public ProductElasticEntity findById(String id) {
        Optional<ProductElasticEntity> product = null;
        try{
           product = productRepo.findById(id);
        } catch(Exception e) {
            logger.info("Find by id failed with message  {}", e.getMessage());
        }
        if(product.isEmpty()) {
            return new ProductElasticEntity();
        }
        return product.get();
    }

    @Override
    public ProductElasticEntity saveEntity(ProductElasticEntity elasticEntity) {
        ProductElasticEntity product = new ProductElasticEntity();
        try {
           product = productRepo.save(elasticEntity);
        } catch(Exception e) {
            logger.info(e.getMessage());
        }
        return  product;
    }

    @Override
    public List<String> saveBulk(List<ProductElasticEntity> products) {
        try{
           Iterable<ProductElasticEntity> productsResponse = productRepo.saveAll(products);
           List<String> savedProductList = new ArrayList<>();
           productsResponse.forEach(pro -> {
               savedProductList.add(pro.getProductId());
           });
           return savedProductList;
        } catch(Exception e) {
            logger.info("Failed with message {}", e.getMessage());
        }
        return List.of("Failed to save");
    }

    @Override
    public List<ProductElasticEntity> getAll() {
        List<ProductElasticEntity> productList = new ArrayList<>();
        try{
            Iterable<ProductElasticEntity> products = productRepo.findAll();
            products.forEach(productList::add);
        } catch (Exception e) {
            logger.info("Failed for message{}", e.getMessage());
        }
        return productList;
    }
}
