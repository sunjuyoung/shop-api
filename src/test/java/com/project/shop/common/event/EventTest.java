package com.project.shop.common.event;

import com.project.shop.common.event.payload.CommentCreatedEventPayload;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
class EventTest {



    @Test
    void test(){

        CommentCreatedEventPayload payload = CommentCreatedEventPayload.builder()
                .commentId(1L)
                .productId(2L)
                .customerId(3L)
                .content("content")
                .build();

        Event<EventPayload> eventPayloadEvent = Event.of(
                123L,
                EventType.COMMENT_CREATED,
                payload
        );

        String json = eventPayloadEvent.to_json();
        System.out.printf("json = %s\n", json);

        Event<EventPayload> eventPayloadEvent1 = Event.fromJson(json);

        assertEquals(eventPayloadEvent.getEventId(), eventPayloadEvent1.getEventId());
    }

}