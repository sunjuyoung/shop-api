package com.project.shop.hotproduct.service.eventhandler;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventType;
import com.project.shop.common.event.payload.CommentCreatedEventPayload;
import com.project.shop.hotproduct.repository.ProductCommentCountCache;
import com.project.shop.hotproduct.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreatedEventHandler implements EventHandler<CommentCreatedEventPayload> {

private final ProductCommentCountCache productCommentCountRepository;

    @Override
    public void handle(Event<CommentCreatedEventPayload> event) {
        CommentCreatedEventPayload payload = event.getPayload();
        productCommentCountRepository.createOrUpdate(payload.getProductId(),
                payload.getProductCommentCount(),
                TimeCalculatorUtils.calculateDurationToMidnight());
    }

    @Override
    public boolean supports(Event<CommentCreatedEventPayload> event) {
        return EventType.COMMENT_CREATED == event.getType();
    }


    @Override
    public Long findProductId(Event<CommentCreatedEventPayload> event) {
        return event.getPayload().getProductId();
    }
}
