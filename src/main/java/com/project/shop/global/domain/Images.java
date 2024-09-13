package com.project.shop.global.domain;

import com.project.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "images")
public class Images extends BaseTime{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public void setProduct(Product product){
        this.product = product;
    }


}
