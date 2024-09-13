package com.project.shop.cart.entity;

import com.project.shop.customer.entity.Customer;
import com.project.shop.global.domain.BaseTime;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "cart" ,indexes = @Index(columnList = "customer_id"))
public class Cart extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @Column(name = "total_price")
    private int totalPrice;

}
