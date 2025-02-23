package com.project.shop.comment.controller;

import com.project.shop.comment.service.CommentService;
import com.project.shop.comment.service.request.CommentCreateRequest;
import com.project.shop.comment.service.response.CommentPageResponse;
import com.project.shop.comment.service.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/v1/comments")
    public Long createComment(@RequestBody CommentCreateRequest request){
        return commentService.create(request);
    }

    @GetMapping("/v1/comments")
    public CommentPageResponse readCommentsPage(@RequestParam Long productId,
                                                  @RequestParam Long page,
                                                  @RequestParam Long pageSize){
        return commentService.findAllWithCustomer(productId, page, pageSize);
    }

    @DeleteMapping("/v1/comments")
    public void delete(@RequestParam Long commentId){
        commentService.delete(commentId);
    }
}
