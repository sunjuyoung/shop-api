package com.project.shop.common.outboxmessagerelay;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventPayload;
import com.project.shop.common.event.EventType;
import com.project.shop.common.snowflake.Snowflake;
import com.project.shop.hotproduct.service.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {

//    private final Snowflake outboxSnowflake = new Snowflake();
   private final Snowflake eventIdSnowflake = new Snowflake();
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(EventType type, EventPayload payload, Long shardKey) {

//        Outbox outbox = Outbox.create(
//                outboxSnowflake.nextId(),
//                type,
//                Event.of(
//                        eventIdSnowflake.nextId(), type, payload
//                ).to_json(),
//                shardKey % MessageRelayConstants.SHARD_COUNT
//        );


       // applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));

        applicationEventPublisher.publishEvent(Event.of(
                eventIdSnowflake.nextId(), type, payload
        ));
    }
}
