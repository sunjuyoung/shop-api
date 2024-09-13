package com.project.shop.payment.repository;

import com.project.shop.payment.domain.PaymentOrder;
import com.project.shop.payment.domain.enums.PaymentOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder,Long> {


    @Modifying
    @Query("update PaymentOrder p set p.paymentOrderStatus =:status, p.updatedAt = CURRENT_TIMESTAMP" +
            " where p.orderKey = :orderKey")
    void updatePaymentOrderStatusByOrderId(@Param("orderKey") String orderKey, @Param("status") PaymentOrderStatus status);
}
