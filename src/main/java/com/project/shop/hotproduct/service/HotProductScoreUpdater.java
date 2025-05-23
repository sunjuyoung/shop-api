package com.project.shop.hotproduct.service;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventPayload;
import com.project.shop.common.event.EventType;
import com.project.shop.hotproduct.repository.HotProductListRepository;
import com.project.shop.hotproduct.repository.ProductCreatedTimeCache;
import com.project.shop.hotproduct.service.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class HotProductScoreUpdater {

    private final HotProductListRepository hotProductListRepository;
    private final HotProductScoreCalculator hotProductScoreCalculator;

    private static final long HOT_PRODUCT_COUNT = 10;
    private static final Duration HOT_PRODUCT_TTL = Duration.ofDays(2);


    public void update(Event<EventPayload> event, EventHandler<EventPayload> eventHandler) {
        Long productId = eventHandler.findProductId(event);


        //댓글 수 저장
        eventHandler.handle(event);

        //점수 계산
        long score = hotProductScoreCalculator.calculate(productId);
        //점수 업데이트
        hotProductListRepository.add(
                productId,
                LocalDateTime.now(),
                score,
                HOT_PRODUCT_COUNT,
                HOT_PRODUCT_TTL
        );
    }

    private boolean isArticleCreatedToday(LocalDateTime createdAt) {
        return createdAt.isAfter(LocalDateTime.now().minusMonths(2)); // 2달 전까지의 데이터만 유효
    }

}
