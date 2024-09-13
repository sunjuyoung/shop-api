package com.project.shop.order.entity;

import com.project.shop.customer.entity.Customer;
import com.project.shop.global.domain.BaseTime;
import com.project.shop.order.entity.enums.DeliveryStatus;
import com.project.shop.order.entity.enums.OrderState;
import com.project.shop.orderItem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity(name = "orders")
@Table(name = "orders" ,indexes = @Index(columnList = "customer_id"))
public class Order extends BaseTime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Embedded
    private ShippingInfo shippingInfo;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    //연관관계 메서드
    public void setOrderItems(OrderItem orderItems){
        this.orderItems.add(orderItems);
        orderItems.setOrder(this);
    }


    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
    }

    public Order(Customer customer,
                 ShippingInfo shippingInfo,
                 OrderState orderState,
                 DeliveryStatus deliveryStatus) {
        this.customer = customer;
        this.shippingInfo = shippingInfo;
        this.orderState = orderState;
        this.deliveryStatus = deliveryStatus;
        this.totalPrice = BigDecimal.ZERO;

    }

    public void setTotalPrice(){

        for (OrderItem orderItem : orderItems) {
           totalPrice = totalPrice.add(orderItem.getAmounts());
        }
    }

    public void cancel(){
        this.orderState = OrderState.CANCELLED;
        this.deliveryStatus = DeliveryStatus.CANCELLED;
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }

    }


}
