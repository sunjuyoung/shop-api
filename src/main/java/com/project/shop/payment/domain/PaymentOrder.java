package com.project.shop.payment.domain;

import com.project.shop.global.domain.BaseTime;
import com.project.shop.payment.domain.enums.PaymentOrderStatus;
import com.project.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_order")
public class PaymentOrder extends BaseTime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String orderKey;

    @Enumerated(EnumType.STRING)
    private PaymentOrderStatus paymentOrderStatus; //not_started, executing, success, failure


    private boolean isLedgerUpdated;
    private boolean isWalletUpdated;
    private boolean isPaymentDone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_event_id")
    private PaymentEvent paymentEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;




    private int failedCount; //실패 횟수
    private int threshold; //실패 임계치


    public void setPaymentEvent(PaymentEvent paymentEvent) {
        this.paymentEvent = paymentEvent;
    }

    public void setPaymentStatus(PaymentOrderStatus paymentOrderStatus) {
        this.paymentOrderStatus = paymentOrderStatus;
    }
}
