package com.project.shop.payment.service;


import com.project.shop.global.exception.PaymentAlreadyException;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.payment.domain.PaymentOrder;
import com.project.shop.payment.domain.enums.PaymentOrderStatus;
import com.project.shop.payment.dto.request.PaymentConfirmCommand;
import com.project.shop.payment.repository.PaymentEventRepository;
import com.project.shop.payment.vo.PaymentStatusUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentOrderStatusService {

    private final PaymentEventRepository paymentEventRepository;


    @Transactional
    public  void updatePaymentOrderStatus(List<PaymentOrder> paymentOrders, PaymentConfirmCommand command) {
        updateStatus(paymentOrders);
        updatePaymentKey(command);
    }

    private void updatePaymentKey(PaymentConfirmCommand command) {
        paymentEventRepository.updatePaymentKeyByOrderId(command.getPaymentKey(), command.getOrderId());
    }

    private  void updateStatus(List<PaymentOrder> paymentOrders) {
        paymentOrders.stream().forEach(paymentOrder -> {
            if(paymentOrder.getPaymentOrderStatus() == PaymentOrderStatus.NOT_STARTED ||
                    paymentOrder.getPaymentOrderStatus() == PaymentOrderStatus.EXECUTING){
                paymentOrder.setPaymentStatus(PaymentOrderStatus.EXECUTING);
            }else  if(paymentOrder.getPaymentOrderStatus() == PaymentOrderStatus.SUCCESS) {
                throw new PaymentAlreadyException(ExceptionCode.PAYMENT_ALREADY_COMPLETE);
            } else if(paymentOrder.getPaymentOrderStatus() == PaymentOrderStatus.FAILURE){
                throw new PaymentAlreadyException(ExceptionCode.PAYMENT_ALREADY_FAIL);
            }
        });
    }


}
