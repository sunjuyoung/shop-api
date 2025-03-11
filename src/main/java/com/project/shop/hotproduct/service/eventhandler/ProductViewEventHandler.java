package com.project.shop.hotproduct.service.eventhandler;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventType;
import com.project.shop.common.event.payload.ProductUnLikedEventPayload;
import com.project.shop.common.event.payload.ProductViewEventPayload;
import com.project.shop.hotproduct.repository.ProductViewedCountCache;
import com.project.shop.hotproduct.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ProductViewEventHandler implements EventHandler<ProductViewEventPayload> {


    private final ProductViewedCountCache productViewCountRepository;


    @Override
    public void handle(Event<ProductViewEventPayload> event) {
        ProductViewEventPayload payload = event.getPayload();
        productViewCountRepository.createOrUpdate(payload.getProductId(),
                payload.getViewCount(),
                TimeCalculatorUtils.calculateDurationToMidnight());
    }

    @Override
    public boolean supports(Event<ProductViewEventPayload> event) {
        return EventType.PRODUCT_VIEWED == event.getType();
    }

    @Override
    public Long findProductId(Event<ProductViewEventPayload> event) {
        return event.getPayload().getProductId();
    }




}
