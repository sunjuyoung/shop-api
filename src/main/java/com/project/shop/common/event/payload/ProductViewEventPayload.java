package com.project.shop.common.event.payload;


import com.project.shop.common.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewEventPayload implements EventPayload {

    private Long productId;
    private Long viewCount;
}
