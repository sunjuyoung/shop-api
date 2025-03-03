package com.project.shop.hotproduct.service.response;

import com.project.shop.hotproduct.client.ProductClient;
import com.project.shop.product.dto.response.ProductViewDTO;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HotProductResponse {


    private Long productId;
    private String name;
    private LocalDateTime createdAt;

    public static HotProductResponse from (ProductViewDTO productViewDTO){
        HotProductResponse hotProductResponse = new HotProductResponse();
        hotProductResponse.productId = productViewDTO.getId();
        hotProductResponse.name = productViewDTO.getName();
        hotProductResponse.createdAt = productViewDTO.getCreatedAt();
        return hotProductResponse;
    }
}
