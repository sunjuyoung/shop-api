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
public class CommentCreatedEventPayload implements EventPayload {

    private Long commentId;
    private Long productId;
    private Long customerId;
    private String content;
    private Long parentCommentId;
    private boolean deleted;
    private Long productCommentCount;
    private LocalDateTime createdAt;
}
