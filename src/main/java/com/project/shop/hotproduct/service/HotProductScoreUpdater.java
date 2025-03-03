package com.project.shop.hotproduct.service;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventPayload;
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
    private final ProductCreatedTimeCache productCreatedTimeRepository;

    private static final long HOT_PRODUCT_COUNT = 10;
    private static final Duration HOT_PRODUCT_TTL = Duration.ofDays(7);


    public void update(Event<EventPayload> event, EventHandler<EventPayload> eventHandler) {

        Long productId = eventHandler.findProductId(event);

        //생성시간 확인
        //LocalDateTime createdTime = productCreatedTimeRepository.read(productId);

//        if(!isArticleCreatedToday(createdTime)){
//            return;
//        }

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

}
