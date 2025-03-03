package com.project.shop.hotproduct.service.eventhandler;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventType;
import com.project.shop.common.event.payload.CommentDeletedEventPayload;
import com.project.shop.hotproduct.repository.ProductCommentCountCache;
import com.project.shop.hotproduct.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDeletedEventHandler implements EventHandler<CommentDeletedEventPayload> {


    private final ProductCommentCountCache productCommentCountRepository;

    @Override
    public void handle(Event<CommentDeletedEventPayload> event) {
        CommentDeletedEventPayload payload = event.getPayload();
        productCommentCountRepository.createOrUpdate(payload.getProductId(),
                payload.getProductCommentCount(),
                TimeCalculatorUtils.calculateDurationToMidnight());
    }

    @Override
    public boolean supports(Event<CommentDeletedEventPayload> event) {
        return EventType.COMMENT_DELETED == event.getType();
    }

    @Override
    public Long findProductId(Event<CommentDeletedEventPayload> event) {
        return event.getPayload().getProductId();
    }

}
