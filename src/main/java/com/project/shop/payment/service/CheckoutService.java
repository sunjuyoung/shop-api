package com.project.shop.payment.service;

import com.project.shop.order.entity.Order;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.payment.domain.PaymentEvent;
import com.project.shop.payment.domain.PaymentOrder;
import com.project.shop.payment.domain.enums.PaymentOrderStatus;
import com.project.shop.payment.dto.request.CheckoutRequest;
import com.project.shop.payment.dto.response.CheckoutResult;
import com.project.shop.payment.repository.PaymentEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutService {

    private final OrderRepository orderRepository;
    private final PaymentEventRepository paymentEventRepository;



    public Long checkout(CheckoutRequest request){

        
        Order order = orderRepository.findOrderWithOrderItemAndProductById(request.getOrderId());

        PaymentEvent paymentEvent = createPaymentEvent(order, request);

        PaymentEvent newPaymentEvent = paymentEventRepository.save(paymentEvent);

        return newPaymentEvent.getId();

    }


    private PaymentEvent createPaymentEvent(Order order, CheckoutRequest request){

        PaymentEvent paymentEvent = PaymentEvent.builder()
                .order(order)
                .orderKey(request.getOrderId() + "_testNumber")
                .orderName(request.getOrderName())
                .totalAmount(request.getAmount())
                .build();

        List<PaymentOrder> paymentOrders = order.getOrderItems().stream().map(o -> {
            PaymentOrder build = PaymentOrder.builder()
                    .product(o.getProduct())
                    .orderKey(request.getOrderId() + "_testNumber")
                    .amount(o.getAmounts())
                    .paymentOrderStatus(PaymentOrderStatus.NOT_STARTED)
                    .build();
            return build;
        }).collect(Collectors.toList());

        paymentOrders.forEach(paymentOrder -> {
            paymentEvent.addPaymentOrder(paymentOrder);
        });
        return paymentEvent;

    }
}
