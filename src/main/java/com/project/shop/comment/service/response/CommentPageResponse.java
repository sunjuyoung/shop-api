package com.project.shop.comment.service.response;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
public class CommentPageResponse {

    private List<CommentResponse> comments;
    private  Long totalCommentCount;


    public static CommentPageResponse from(List<CommentResponse> comments, Long totalCommentCount){
        CommentPageResponse commentPageResponse = new CommentPageResponse();
        commentPageResponse.comments = comments;
        commentPageResponse.totalCommentCount = totalCommentCount;
        return commentPageResponse;
    }
}
