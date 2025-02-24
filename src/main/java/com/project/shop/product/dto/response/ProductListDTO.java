package com.project.shop.product.dto.response;

import com.project.shop.global.domain.Images;
import com.project.shop.product.entity.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProductListDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private int discountRate;
    private String description;
    private List<String> fileName;
    private Long commentCount;
    private Long likeCount;
    private String categoryName;
    private Long categoryId;
    private String mainImgUrl;



    public ProductListDTO(Long id, String name, BigDecimal price, int discountRate, String description,
                          String fileName, Long commentCount, Long likeCount, String categoryName, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountRate = discountRate;
        this.description = description;
        this.mainImgUrl = fileName;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public ProductListDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.discountRate = product.getDiscountRate();
//        this.quantity = product.getQuantity();
//        this.viewCount = product.getViewCount();
//        this.mdRecommended = product.isMdRecommended();
//        this.categoryName = product.getCategory().getName();
        this.fileName = product.getProductImages().stream()
                .map(Images::getFileName)
                .collect(Collectors.toList());

        this.categoryName = product.getCategory().getName();
        this.categoryId = product.getCategory().getId();
    }



}
