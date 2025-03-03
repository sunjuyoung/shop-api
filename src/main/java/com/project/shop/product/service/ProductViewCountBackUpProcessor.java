package com.project.shop.product.service;

import com.project.shop.common.event.EventType;
import com.project.shop.common.event.payload.ProductViewEventPayload;
import com.project.shop.common.outboxmessagerelay.OutboxEventPublisher;
import com.project.shop.product.entity.ProductViewCount;
import com.project.shop.product.repository.ProductViewCountBackUpRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductViewCountBackUpProcessor {

    private final ProductViewCountBackUpRepository productViewCountBackUpRepository;
    private final OutboxEventPublisher outboxEventPublisher;;

    @Transactional
    public void backup(Long productId, Long count){
        int result = productViewCountBackUpRepository.updateViewCount(productId, count);
        if(result == 0){
            productViewCountBackUpRepository.save(ProductViewCount.create(productId, count));
        }
        outboxEventPublisher.publish(
                EventType.PRODUCT_VIEWED,
                ProductViewEventPayload.builder()
                        .productId(productId)
                        .viewCount(count)
                        .build(),
                productId
        );
    }
}
