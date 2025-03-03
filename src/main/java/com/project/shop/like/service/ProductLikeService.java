package com.project.shop.like.service;

import com.project.shop.common.event.EventType;
import com.project.shop.common.event.payload.ProductLikedEventPayload;
import com.project.shop.common.event.payload.ProductUnLikedEventPayload;
import com.project.shop.common.outboxmessagerelay.OutboxEventPublisher;
import com.project.shop.customer.entity.Customer;
import com.project.shop.customer.repository.CustomerRepository;
import com.project.shop.global.exception.NotFoundLike;
import com.project.shop.global.exception.ProductNotFoundException;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.like.entity.ProductLike;
import com.project.shop.like.entity.ProductLikeCount;
import com.project.shop.like.repository.ProductLikeCountRepository;
import com.project.shop.like.repository.ProductLikeRepository;
import com.project.shop.like.service.response.ProductLikeResponse;
import com.project.shop.product.entity.Product;
import com.project.shop.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductLikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    private final ProductLikeCountRepository productLikeCountRepository;

    private final OutboxEventPublisher outboxEventPublisher;

    public ProductLikeResponse read(Long productId, Long customerId){
        return productLikeRepository.findByUserIdAndProductId(customerId, productId)
                .map(ProductLikeResponse::create)
                .orElse(null)
                ;
    }



    @Transactional
    public void like(Long productId, Long customerId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ExceptionCode.NOT_FOUND_PRODUCT));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("customer not found"));
        // 👍 좋아요 정보 저장
        ProductLike productLike = productLikeRepository.save(ProductLike.create(product, customer));

        // 👍 카운트 증가  ,낙관적 락 3번 재시도 200ms 간격
        increaseLikeCount(productId);

        // 👍 좋아요 이벤트 발행
        outboxEventPublisher.publish(
                EventType.PRODUCT_LIKED,
                ProductLikedEventPayload.builder()
                        .productId(productId)
                        .customerId(customerId)
                        .productLikeId(productLike.getId())
                        .productLikeCount(count(productId))
                        .createdAt(productLike.getCreatedAt())
                        .build(),
                productId

        );
    }

    @Transactional
    @Retryable(
            value = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 200)
    )
    public void increaseLikeCount(Long productId) {
        ProductLikeCount productLikeCount = productLikeCountRepository.findById(productId)
                .orElse(ProductLikeCount.init(productId, 0L)); //상품 생성시 미리 0으로 초기화 해두면 좋을 것 같다.

        productLikeCount.increase();
        productLikeCountRepository.save(productLikeCount);
    }
    //@Recover를 사용하면 실패한 경우 별도의 대응이 가능하므로 더 안전한 시스템을 만들 수 있음

    @Transactional
    public void unlike(Long productId, Long customerId){
       productLikeRepository.findByUserIdAndProductId(customerId, productId)
                .ifPresent((productLike) ->{
                        productLikeRepository.delete(productLike);
                    ProductLikeCount productLikeCount = productLikeCountRepository.findById(productId)
                            .orElseThrow();
                    productLikeCount.decrease();


                    outboxEventPublisher.publish(
                            EventType.PRODUCT_UNLIKED,
                            ProductUnLikedEventPayload.builder()
                                    .productId(productId)
                                    .customerId(customerId)
                                    .productLikeId(productLike.getId())
                                    .productLikeCount(count(productId))
                                    .createdAt(productLike.getCreatedAt())
                                    .build(),
                            productId

                    );
                });

    }

    public Long count(Long productId){
        return productLikeCountRepository.findById(productId)
                .map(ProductLikeCount::getLikeCount)
                .orElse(0L);
    }
}
