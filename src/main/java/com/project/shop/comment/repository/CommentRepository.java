package com.project.shop.comment.repository;

import com.project.shop.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long>, CustomCommentRepository {



    @Query(
            value = "select comment.comment_id,  comment.content, comment.parent_comment_id" +
                    "  from  (select  comment_id " +
                    " from commment c " +
                    " where  product_id = :productId" +
                    " order by parent_comment_id asc, comment_id asc" +
                    " limit :limit offset :offset) t left join comment on t.comment_id = comment.comment_id" ,
            nativeQuery = true
    )
    List<Comment> findAll(
            @Param("productId") Long productId,
            @Param("offset") Long offset,
            @Param("limit") Long limit
    );

    @EntityGraph(attributePaths = {"product","customer"})
    @Query("select c from Comment c where c.id = :commentId")
    Optional<Comment> findCommentWithProductById(Long commentId);







}
