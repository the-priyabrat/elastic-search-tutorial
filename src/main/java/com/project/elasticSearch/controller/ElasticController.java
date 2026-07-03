package com.project.elasticSearch.controller;

import com.project.elasticSearch.entity.ProductElasticEntity;
import com.project.elasticSearch.dto.SearchResponse;
import com.project.elasticSearch.elasticservice.ElasticSearchService;
import com.project.elasticSearch.elasticservice.ElasticService;
import com.project.elasticSearch.elasticservice.ElasticSortingAndPaginationService;
import com.project.elasticSearch.elasticservice.ElasticWithinRangeSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/elastic")
public class ElasticController {

    private final ElasticService elasticService;

    private final Map<String, ElasticSearchService> elasticBeans;
    private final ElasticWithinRangeSearch rangeSearch;
    private final ElasticSortingAndPaginationService sortingService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<ProductElasticEntity> getById(@PathVariable String id) {
        return new ResponseEntity<ProductElasticEntity>(elasticService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ProductElasticEntity> save(@RequestBody ProductElasticEntity productElastic) {
        return new ResponseEntity<ProductElasticEntity>(elasticService.saveEntity(productElastic), HttpStatus.OK);
    }

    @PostMapping("/saveBulk")
    public ResponseEntity<List<String>> saveAllProduct(@RequestBody List<ProductElasticEntity> products) {
        return new ResponseEntity<List<String>>(elasticService.saveBulk(products), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductElasticEntity>> getAll() {
        return new ResponseEntity<List<ProductElasticEntity>>(elasticService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> matchSearch(@RequestParam String keyWord, @RequestParam String searchType) {
        return new ResponseEntity<SearchResponse>(elasticBeans.get(searchType).searchProduct(keyWord), HttpStatus.OK);
    }

    @GetMapping("/range")
    public ResponseEntity<SearchResponse> rangeSearch(@RequestParam Double first, @RequestParam Double last) {
        return new ResponseEntity<SearchResponse>(rangeSearch.searchWithinRanges(first, last), HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<SearchResponse> sortProduct(@RequestParam int first, @RequestParam int last) {
        return new ResponseEntity<SearchResponse>(sortingService.sortProduct(first, last), HttpStatus.OK);
    }
}
