package com.project.shop.product.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductViewDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private int viewCount;

    private Long categoryId;
    private String categoryName;

    private List<ProductImagesDTO> productImages;


    public ProductViewDTO() {
    }


    @QueryProjection
    public ProductViewDTO(Long id, String name, String description, BigDecimal price, int stockQuantity, int viewCount, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.viewCount = viewCount;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

//    @QueryProjection
//    public ProductViewDTO(Long id, String name, String description, int price, int stockQuantity, int viewCount) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.price = price;
//        this.stockQuantity = stockQuantity;
//        this.viewCount = viewCount;
//    }
}
