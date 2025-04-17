package com.project.shop.hotproduct.service;

import com.project.shop.common.event.Event;
import com.project.shop.common.event.EventPayload;
import com.project.shop.hotproduct.client.ProductClient;
import com.project.shop.hotproduct.repository.HotProductListRepository;
import com.project.shop.hotproduct.service.eventhandler.EventHandler;
import com.project.shop.hotproduct.service.response.HotProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotProductService {

    private final ProductClient productClient;
    private final List<EventHandler> eventHandlers;
    private final HotProductScoreUpdater hotProductScoreUpdater;
    private final HotProductListRepository hotProductListRepository;

    //이벤트를 카프카를 통해서 전달 받는다
    //이벤트를 통해서 인기글 점수 계산, hotProductListRepository 에 인기글 id 저장
    //consumer에서 이벤트를 받아서 처리
//    public void handleEvent(Event<EventPayload> event){
//        EventHandler<EventPayload> eventHandler = findEventHandler(event);
//        if(eventHandler == null){
//            log.error("Unsupported event={}", event);
//            return;
//        }
//            hotProductScoreUpdater.update(event,eventHandler);
//    }


    //전달받은 이벤트가 지원하는 이벤트 핸들러 찾기
    private EventHandler<EventPayload> findEventHandler(Event<EventPayload> event) {
        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.supports(event))
                .findAny()
                .orElse(null)
                ;
    }



    public List<HotProductResponse> readAll(String dateStr){
        // yyyyMMdd

        return hotProductListRepository.readAll(dateStr)
                .stream()
                .map(productClient::read)
                .filter(Objects::nonNull)
                .map(HotProductResponse::from)
                .toList();
    }

}
