package com.project.shop.hotproduct.service.eventhandler;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventPayload;

public interface EventHandler <T extends EventPayload> {
    void handle(Event<T> event);

    boolean supports(Event<T> event);

    Long findProductId(Event<T> event);

}
