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
    private static final String TEST_ORDER_KEY = "_testNumber";



    public Long checkout(CheckoutRequest request){

        //주문, 상품들 조회
        Order order = orderRepository.findOrderWithOrderItemAndProductById(request.getOrderId());

        PaymentEvent paymentEvent = createPaymentEvent(order, request);

        //CascadeType.ALL로 인해 PaymentEvent 저장시 PaymentOrder도 같이 저장
        PaymentEvent newPaymentEvent = paymentEventRepository.save(paymentEvent);

        return newPaymentEvent.getId();

    }


    private PaymentEvent createPaymentEvent(Order order, CheckoutRequest request){

        //PaymentEvent 생성
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .order(order)
                .orderKey(request.getOrderId() + TEST_ORDER_KEY)
                .orderName(request.getOrderName())
                .totalAmount(request.getAmount())
                .build();

        //PaymentOrder 생성
        List<PaymentOrder> paymentOrders = createPaymentOrders(order, request);


        //PaymentEvent에 PaymentOrder 추가
        paymentOrders.forEach(paymentOrder -> {
            paymentEvent.addPaymentOrder(paymentOrder);
        });

        return paymentEvent;

    }

    private static List<PaymentOrder> createPaymentOrders(Order order, CheckoutRequest request) {

        return order.getOrderItems().stream()
                .map(o -> PaymentOrder.builder()
                        .product(o.getProduct())
                        .orderKey(request.getOrderId() + TEST_ORDER_KEY)
                        .amount(o.getAmounts())
                        .paymentOrderStatus(PaymentOrderStatus.NOT_STARTED)
                        .build())
                .collect(Collectors.toList());
    }
}
