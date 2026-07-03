package com.project.elasticSearch.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document(indexName = "products")
public class ProductElasticEntity {

    @Id
    @Field(type = FieldType.Keyword)
    private String productId;

    @Field(type = FieldType.Text)
    private String productName;

    @Field(type = FieldType.Double)
    private Double productPrice;

    @Field(type = FieldType.Text)
    private String productCategory;

    @Field(type = FieldType.Text)
    private String productDescription;

}
