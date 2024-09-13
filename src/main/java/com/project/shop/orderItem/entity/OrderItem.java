package com.project.shop.orderItem.entity;

import com.project.shop.global.domain.BaseTime;
import com.project.shop.order.entity.Order;
import com.project.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "amounts")
    private BigDecimal amounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    //연관메서드
    public void setOrder(Order order){
        this.order = order;
    }


    public void setAmounts(){
      this.amounts =  this.price.multiply(BigDecimal.valueOf(quantity));
    }

    // 생성 메서드
    public static OrderItem createOrderItem(Product product, int quantity) {
        // 개별 상품 가격
        BigDecimal price = product.getPrice();

        // 총 가격 계산
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

        // OrderItem 생성
        OrderItem orderItem = new OrderItem();
        orderItem.product = product;
        orderItem.quantity = quantity;
        orderItem.price = price;
        orderItem.amounts = totalPrice;

        return orderItem;
    }


    public void cancel(){
        getProduct().addStock(quantity);
    }


}
