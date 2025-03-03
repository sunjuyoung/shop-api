package com.project.shop.hotproduct.service.eventhandler;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventType;
import com.project.shop.common.event.payload.ProductLikedEventPayload;
import com.project.shop.hotproduct.repository.ProductLikeCountCache;
import com.project.shop.hotproduct.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductLikedEventHandler implements EventHandler<ProductLikedEventPayload> {


    private final ProductLikeCountCache productLikeCountRepository;

    @Override
    public void handle(Event<ProductLikedEventPayload> event) {
        ProductLikedEventPayload payload = event.getPayload();

        productLikeCountRepository.createOrUpdate(payload.getProductId(),
                payload.getProductLikeCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
                );

    }

    @Override
    public boolean supports(Event<ProductLikedEventPayload> event) {
        return EventType.PRODUCT_LIKED == event.getType();
    }

    @Override
    public Long findProductId(Event<ProductLikedEventPayload> event) {
        return event.getPayload().getProductId();
    }
}
