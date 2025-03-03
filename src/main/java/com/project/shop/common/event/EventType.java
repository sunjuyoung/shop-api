package com.project.shop.common.event;


import com.project.shop.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {


    COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.PRODUCT_COMMENT),
    COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.PRODUCT_COMMENT),
    PRODUCT_VIEWED(ProductViewEventPayload.class, Topic.PRODUCT_VIEW),
    PRODUCT_LIKED(ProductLikedEventPayload.class, Topic.PRODUCT_LIKE),
    PRODUCT_UNLIKED(ProductUnLikedEventPayload.class, Topic.PRODUCT_LIKE);

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    // EventType에 해당하는 Topic을 반환
    public static EventType from(String type){
        try{
            return valueOf(type);
        }catch (Exception e){
            log.error("Failed to get EventType from {}", type, e);
            return null;
        }
    }

    public static class Topic{
        public static final String PRODUCT_COMMENT = "product-comment";
        public static final String PRODUCT_LIKE = "product-like";
        public static final String PRODUCT_VIEW = "product-view";
    }

}
