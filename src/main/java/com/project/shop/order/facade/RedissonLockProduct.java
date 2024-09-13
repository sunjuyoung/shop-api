package com.project.shop.order.facade;

import com.project.shop.product.entity.Product;
import com.project.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockProduct {

    private final RedissonClient redissonClient;
    private final ProductService productService;


    public void decrease(Product product, int quantity){
        RLock lock = redissonClient.getLock(product.getId().toString());
        try {
            boolean available = lock.tryLock(10,1, TimeUnit.SECONDS);
            if(!available){
                log.info("재고lock 획득 실패");
                return;
            }
            productService.decrease(product,quantity);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
}
