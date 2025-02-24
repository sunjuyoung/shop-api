package com.project.shop.product.service;


import com.project.shop.product.repository.ProductViewCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductViewService {

    private final ProductViewCountRepository productViewCountRepository;
    private final ProductViewCountBackUpProcessor productViewCountBackUpProcessor;
    private static final int BAKCUP_COUNT = 10;

    public Long increase(Long productId){
        Long count = productViewCountRepository.increase(productId);
        if(count % BAKCUP_COUNT == 0){
            productViewCountBackUpProcessor.backup(productId, count);
        }
        return count;

    }

    public Long count(Long productId){
        return productViewCountRepository.read(productId);
    }
}
