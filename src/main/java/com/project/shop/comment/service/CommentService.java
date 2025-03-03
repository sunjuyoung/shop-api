package com.project.shop.comment.service;

import com.project.shop.comment.entity.Comment;
import com.project.shop.comment.entity.ProductCommentCount;
import com.project.shop.comment.repository.CommentRepository;
import com.project.shop.comment.repository.ProductCommentCountRepository;
import com.project.shop.comment.service.request.CommentCreateRequest;
import com.project.shop.comment.service.response.CommentPageResponse;
import com.project.shop.comment.service.response.CommentResponse;
import com.project.shop.common.event.EventType;
import com.project.shop.common.event.payload.CommentCreatedEventPayload;
import com.project.shop.common.outboxmessagerelay.OutboxEventPublisher;
import com.project.shop.customer.entity.Customer;
import com.project.shop.customer.repository.CustomerRepository;
import com.project.shop.global.exception.CommentAlreadyDeletedException;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.product.entity.Product;
import com.project.shop.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static java.util.function.Predicate.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final ProductCommentCountRepository productCommentCountRepository;

    private final OutboxEventPublisher outboxEventPublisher;

    /**
     * 댓글 생성
     * @param request
     * @return
     */
    @Transactional
    public Long create(CommentCreateRequest request){

        //부모 댓글 조회
        Comment parent = findParent(request.getParentCommentId());

        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow();
        Product product = productRepository.findById(request.getProductId()).orElseThrow();

        Comment comment = null;
        if(parent == null) {
             comment = Comment.rootCreate(request.getContent(), customer, product);
        }else {
            comment = Comment.create(request.getContent(),parent.getId(),  customer, product);
        }

        Comment saveComment = commentRepository.save(comment);
        int result = productCommentCountRepository.increase(request.getProductId());
        if(result==0){
            productCommentCountRepository.save(ProductCommentCount.init(request.getProductId(), 1L));
        }
        outboxEventPublisher.publish(
                EventType.COMMENT_CREATED,
                CommentCreatedEventPayload.builder()
                        .commentId(saveComment.getId())
                        .productId(request.getProductId())
                        .productCommentCount(count(request.getProductId()))
                        .createdAt(saveComment.getCreatedAt())
                        .deleted(saveComment.getDeleted())
                        .content(saveComment.getContent())
                        .customerId(customer.getId())
                        .build()
                    , product.getId()

        );



        return saveComment.getId();
    }

    private Comment findParent(Long parentCommentId) {
        if(parentCommentId == null){
            return null;
        }
        return commentRepository.findById(parentCommentId)
                .filter(not(Comment::getDeleted))
                .filter(Comment::isRoot)
                .orElseThrow(()->new CommentAlreadyDeletedException(ExceptionCode.ALREADY_DELETED_COMMENT));

    }


    public CommentResponse read(Long commnetId){
        Comment comment = commentRepository.findById(commnetId).orElseThrow();
        return CommentResponse.from(comment);
    }

    public CommentPageResponse  findAllWithCustomer(Long productId, Long page, Long pageSize){

        List<Comment> comments = commentRepository.findAllWithCustomerAndProduct(productId, (page - 1) * pageSize, pageSize);

        List<CommentResponse> list = comments.stream()
                .map(CommentResponse::from)
                .toList();

        Long count = commentRepository.countAll(productId, PageCalculator.calculatePage(page, pageSize, 10L));

        return CommentPageResponse.from(list, count);
    }

    public Long count(Long productId){
        return productCommentCountRepository.findById(productId)
                .map(ProductCommentCount::getCommentCount)
                .orElse(0L);
    }


    @Transactional
    public void delete(Long commentId) {
        commentRepository.findCommentWithProductById(commentId)
                .filter(not(Comment::getDeleted))
                .ifPresent((comment) -> {
                    comment.delete();
                    productCommentCountRepository.decrease(comment.getProduct().getId());

                    outboxEventPublisher.publish(
                            EventType.COMMENT_DELETED,
                            CommentCreatedEventPayload.builder()
                                    .commentId(comment.getId())
                                    .productId(comment.getProduct().getId())
                                    .productCommentCount(comment.getParentCommentId())
                                    .createdAt(comment.getCreatedAt())
                                    .deleted(comment.getDeleted())
                                    .content(comment.getContent())
                                    .customerId(comment.getCustomer().getId())
                                    .build()
                            , comment.getProduct().getId()

                    );
                });

    }
}
