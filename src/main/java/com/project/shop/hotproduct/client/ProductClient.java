package com.project.shop.hotproduct.client;


import com.project.shop.product.dto.response.ProductViewDTO;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductClient {
//id만 저장된redis 에서 최종  상품 정보를 조회하는 클라이언트


    private RestClient restClient;

    @Value("${endpoints.sun-shop-product-service.url}")
    private String productServiceUrl;

    @PostConstruct
    void initRestClient(){
        restClient = RestClient.create(productServiceUrl);
    }

    public ProductViewDTO read(Long productId){
        try {
            return restClient.get()
                    .uri("/products/{productId}", productId)
                    .retrieve()
                    .body(ProductViewDTO.class);
        }catch (Exception e){
            log.error("Article read articleId={}", productId,e);
        }

        return null;
    }

    @Getter
    public static class ProductResponse{

        private Long productId;
        private String title;
        private LocalDateTime createdAt;
    }

}
