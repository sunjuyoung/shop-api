package com.project.shop.comment.repository;

import com.project.shop.comment.entity.Comment;

import java.util.List;

public interface CustomCommentRepository {

    List<Comment> findAllWithCustomerAndProduct(Long productId, Long offset, Long limit);

    Long countAll(Long productId, Long limit);
}
