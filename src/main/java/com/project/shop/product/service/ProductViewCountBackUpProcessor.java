package com.project.shop.product.service;

import com.project.shop.product.entity.ProductViewCount;
import com.project.shop.product.repository.ProductViewCountBackUpRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductViewCountBackUpProcessor {

    private final ProductViewCountBackUpRepository productViewCountBackUpRepository;

    @Transactional
    public void backup(Long productId, Long count){
        int result = productViewCountBackUpRepository.updateViewCount(productId, count);
        if(result == 0){
            productViewCountBackUpRepository.save(ProductViewCount.create(productId, count));
        }
    }
}
