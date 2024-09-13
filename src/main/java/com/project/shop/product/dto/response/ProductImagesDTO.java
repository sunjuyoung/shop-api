package com.project.shop.product.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ProductImagesDTO {

    private Long id;
    private String fileName;


    @QueryProjection
    public ProductImagesDTO(Long id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }
}
