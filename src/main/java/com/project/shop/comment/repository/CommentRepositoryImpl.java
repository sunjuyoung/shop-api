package com.project.shop.comment.repository;

import com.project.shop.comment.entity.Comment;
import com.project.shop.comment.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.project.shop.comment.entity.QComment.*;

public class CommentRepositoryImpl implements CustomCommentRepository{

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Comment> findAllWithCustomerAndProduct(Long productId, Long offset, Long limit) {

        List<Long> subQuery = queryFactory
                .select(comment.id)
                .from(comment)
                .where(comment.product.id.eq(productId))
                .orderBy(comment.parentCommentId.asc(), comment.id.asc())
                .limit(limit)
                .offset(offset)
                .fetch();


        return queryFactory.
                selectFrom(comment)
                .leftJoin(comment.customer).fetchJoin()
                .leftJoin(comment.product).fetchJoin()
                .where(comment.id.in(subQuery))
                .fetch();

    }

    @Override
    public Long countAll(Long productId, Long limit) {

        return queryFactory
                .select(comment.id)
                .from(comment)
                .where(comment.product.id.eq(productId))
                .limit(limit)
                .fetchCount();

    }
}
