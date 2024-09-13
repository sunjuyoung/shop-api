package com.project.shop.payment.domain;

import com.project.shop.customer.entity.Customer;
import com.project.shop.global.domain.BaseTime;
import com.project.shop.order.entity.Order;
import com.project.shop.payment.domain.enums.PaymentMethod;
import com.project.shop.payment.domain.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_event")
public class PaymentEvent    extends BaseTime{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String orderKey;

    //private Long buyerId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(unique = true)
    private String paymentKey;

    private String orderName;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "psp_raw_data", columnDefinition = "json")
    private String pspRawData;  //PSP 로 부터 받은 원시 데이터

    @Column(name = "approved_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime approvedAt;


    private boolean isPaymentDone;

    private BigDecimal totalAmount;




    @Builder.Default
    @OneToMany(mappedBy = "paymentEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentOrder> paymentOrders = new ArrayList<>();

    public void addPaymentOrder(PaymentOrder paymentOrder) {
        paymentOrders.add(paymentOrder);
        paymentOrder.setPaymentEvent(this);
    }


}
