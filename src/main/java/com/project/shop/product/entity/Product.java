package com.project.shop.product.entity;

import com.project.shop.category.entity.Category;
import com.project.shop.global.domain.BaseTime;
import com.project.shop.global.domain.Images;
import com.project.shop.global.exception.NotEnoughStockException;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.product.dto.request.ProductModifyDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "product")
public class Product extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;


    @Column(name = "discount_rate")
    private int discountRate;

    @Column(nullable = false)
    private int quantity;


    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "md_recommended")
    private boolean mdRecommended;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    @Builder.Default
    @OneToMany(mappedBy = "product",cascade = {CascadeType.REMOVE,  CascadeType.PERSIST})
    private List<Images> productImages = new ArrayList<>();


    private boolean isDeleted;

    public void addImage(Images image) {
        image.setProduct(this);
        productImages.add(image);
    }
    public void addImageString(String fileName){
        Images productImage = Images.builder()
                .fileName(fileName)
                .build();
        addImage(productImage);
    }

    public void clearImage(){
        this.productImages.clear();
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public void modifyProduct(ProductModifyDTO dto){
        this.description = dto.getDescription();
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.quantity = dto.getStockQuantity();

    }


    public void addStock(int quantity){
        this.quantity += quantity;
    }


    public void minusStock(int quantity){
        int restStock = this.quantity - quantity;
        if(restStock< 0){
            throw new NotEnoughStockException(ExceptionCode.NOT_ENOUGH_STOCK);
        }
        this.quantity = restStock;
    }


}
