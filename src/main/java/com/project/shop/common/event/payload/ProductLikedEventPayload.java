package com.project.shop.common.event.payload;

import com.project.shop.common.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductLikedEventPayload implements EventPayload {

    private Long productLikeId;
    private Long productId;
    private Long customerId;
    private Long productLikeCount;
    private LocalDateTime createdAt;


}
