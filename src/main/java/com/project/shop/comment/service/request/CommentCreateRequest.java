package com.project.shop.comment.service.request;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private String content;
    private Long parentCommentId;
    private Long productId;
    private Long customerId;
}
