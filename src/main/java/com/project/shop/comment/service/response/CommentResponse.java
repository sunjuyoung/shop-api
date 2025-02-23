package com.project.shop.comment.service.response;

import com.project.shop.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CommentResponse {


    private Long commentId;
    private String content;
    private Long parentCommentId;
    private Long productId;
    private Long customerId;
    private String customerName;
    private Boolean deleted;
    private LocalDateTime createdAt;


    public static CommentResponse from (Comment comment){
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.commentId = comment.getId();
        commentResponse.content = comment.getContent();
        commentResponse.parentCommentId = comment.getParentCommentId();
       // commentResponse.productId = comment.getProduct().getId();
        commentResponse.customerId = comment.getCustomer().getId();
        commentResponse.customerName = comment.getCustomer().getNickname();
        commentResponse.deleted = comment.getDeleted();
        commentResponse.createdAt = comment.getCreatedAt();
        return commentResponse;
    }

    public static CommentResponse from(Long commentId, String content, Long parentCommentId, Long productId, Long customerId, String customerName, Boolean deleted, LocalDateTime createdAt) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.commentId = commentId;
        commentResponse.content = content;
        commentResponse.parentCommentId = parentCommentId;
        commentResponse.productId = productId;
        commentResponse.customerId = customerId;
        commentResponse.customerName = customerName;
        commentResponse.deleted = deleted;
        commentResponse.createdAt = createdAt;
        return commentResponse;
    }
}
