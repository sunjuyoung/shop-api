package com.project.shop.hotproduct.service.eventhandler;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventType;
import com.project.shop.common.event.payload.ProductUnLikedEventPayload;
import com.project.shop.hotproduct.repository.ProductLikeCountCache;
import com.project.shop.hotproduct.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductUnLikedEventHandler implements EventHandler<ProductUnLikedEventPayload> {

    private final ProductLikeCountCache productLikeCountRepository;


    @Override
    public void handle(Event<ProductUnLikedEventPayload> event) {
        ProductUnLikedEventPayload payload = event.getPayload();
        productLikeCountRepository.createOrUpdate(payload.getProductId(),
                payload.getProductLikeCount(),
                TimeCalculatorUtils.calculateDurationToMidnight());
    }

    @Override
    public boolean supports(Event<ProductUnLikedEventPayload> event) {
        return EventType.PRODUCT_LIKED == event.getType();
    }

    @Override
    public Long findProductId(Event<ProductUnLikedEventPayload> event) {
        return event.getPayload().getProductId();
    }
}
