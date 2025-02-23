package com.project.shop.like.controller;

import com.project.shop.like.service.ProductLikeService;
import com.project.shop.like.service.response.ProductLikeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductLikeController {

    private final ProductLikeService productLikeService;


    @GetMapping("/v1/product-likes/products/{productId}/customer/{customerId}")
    public ProductLikeResponse read(@PathVariable Long productId,
                                    @PathVariable Long customerId){
        return productLikeService.read(productId, customerId);
    }


    @PostMapping("/v1/product-likes/products/{productId}/customer/{customerId}")
    public void like(@PathVariable Long productId,
                     @PathVariable Long customerId){
        productLikeService.like(productId, customerId);
    }

    @DeleteMapping("/v1/product-likes/products/{productId}/customer/{customerId}")
    public void unlike(@PathVariable Long productId,
                       @PathVariable Long customerId){
        productLikeService.unlike(productId, customerId);
    }

    @GetMapping("/v1/product-likes/products/{productId}/count")
    public Long getCount(@PathVariable Long productId){
        return productLikeService.count(productId);
    }
}
