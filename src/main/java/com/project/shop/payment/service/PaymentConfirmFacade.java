package com.project.shop.payment.service;

import com.project.shop.payment.domain.PaymentEvent;
import com.project.shop.payment.domain.PaymentOrder;
import com.project.shop.payment.dto.request.PaymentConfirmCommand;
import com.project.shop.payment.dto.response.PaymentConfirmResult;
import com.project.shop.payment.repository.PaymentEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentConfirmFacade {

    private final PaymentConfirmService paymentConfirmService;
    private final PaymentOrderStatusService paymentOrderStatusService;
    private final PaymentEventRepository paymentEventRepository;


    public PaymentConfirmResult confirmPayment(PaymentConfirmCommand command) {
        PaymentEvent paymentEvent = paymentEventRepository.findAllByOrderKey(command.getOrderId()).orElseThrow();
        List<PaymentOrder> paymentOrders = paymentEvent.getPaymentOrders();

        //PaymentOrderStatus 변경 NOT_STARTED -> EXECUTING
        //paymentKey 업데이트
        paymentOrderStatusService.updatePaymentOrderStatus(paymentOrders,command);

        return paymentConfirmService.confirm(paymentEvent, command);
    }
}
