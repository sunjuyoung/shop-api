package com.project.shop.payment.repository;

import com.project.shop.payment.domain.PaymentEvent;
import com.project.shop.payment.domain.enums.PaymentMethod;
import com.project.shop.payment.domain.enums.PaymentType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PaymentEventRepository extends JpaRepository<PaymentEvent,Long> {

    @EntityGraph(attributePaths = "paymentOrders")
    Optional<PaymentEvent> findAllByOrderKey(String key);

    @Modifying
    @Query("update PaymentEvent p set p.paymentKey = :paymentKey where p.orderKey = :orderKey")
    void updatePaymentKeyByOrderId(@Param("paymentKey") String paymentKey, @Param("orderKey") String orderKey);


    //update_payment_event_extra_details
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update PaymentEvent p " +
            " set p.orderName = :orderName, p.paymentMethod = :method, p.approvedAt =:approvedAt, p.paymentType =:type, p.pspRawData =:pspRawData, p.updatedAt = CURRENT_TIMESTAMP" +
            " where p.orderKey = :orderKey")
    void updatePaymentEventExtraDetails(@Param("orderKey") String orderKey,
                                        @Param("orderName") String orderName,
                                        @Param("method") PaymentMethod method,
                                        @Param("approvedAt") LocalDateTime approvedAt,
                                        @Param("type") PaymentType type,
                                        @Param("pspRawData") String pspRawData);

}
