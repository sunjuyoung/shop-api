package com.project.shop.hotproduct.consumer;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventPayload;
import com.project.shop.common.event.EventType;
import com.project.shop.hotproduct.service.HotProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotProductEventConsumer {

    private final HotProductService hotProductService;

    @KafkaListener(topics = {
            EventType.Topic.PRODUCT_COMMENT,
            EventType.Topic.PRODUCT_LIKE,
            EventType.Topic.PRODUCT_VIEW
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("hotproductconsumer message={}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if(event != null){
            hotProductService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
