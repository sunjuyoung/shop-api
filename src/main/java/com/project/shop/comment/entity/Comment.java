package com.project.shop.comment.entity;


import com.project.shop.customer.entity.Customer;
import com.project.shop.global.domain.BaseTime;
import com.project.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "comment")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Long parentCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Boolean deleted;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;




    public static Comment rootCreate(String content,  Customer customer, Product product){
        Comment comment = new Comment();
        comment.content = content;
        comment.deleted = false;
        comment.customer = customer;
        comment.product = product;
        return comment;
    }

    public static Comment create(String content,Long parentCommentId,  Customer customer, Product product){
        Comment comment = new Comment();
        comment.content = content;
        comment.deleted = false;
        comment.customer = customer;
        comment.product = product;
        comment.parentCommentId = parentCommentId;
        return comment;
    }

    public boolean isRoot(){
        return parentCommentId == null;
    }

    public void delete(){
        deleted = true;
    }

    public void setParentId(Long parentCommentId){
        this.parentCommentId = parentCommentId;
    }

}
