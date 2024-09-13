package com.project.shop.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shop.global.exception.PaymentAlreadyException;
import com.project.shop.global.exception.PaymentAmountException;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.payment.domain.PaymentEvent;
import com.project.shop.payment.domain.PaymentOrder;
import com.project.shop.payment.domain.enums.PaymentOrderStatus;
import com.project.shop.payment.dto.request.PaymentConfirmCommand;
import com.project.shop.payment.dto.request.PaymentExecutionResult;
import com.project.shop.payment.dto.response.PaymentConfirmResult;
import com.project.shop.payment.repository.PaymentEventRepository;
import com.project.shop.payment.repository.PaymentOrderRepository;
import com.project.shop.payment.vo.PaymentStatusUpdateCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentConfirmService {


    private final PaymentEventRepository paymentEventRepository;
    private final TossPaymentService tossPaymentService;

    private final ObjectMapper objectMapper;

    private final PaymentOrderRepository paymentOrderRepository;


    public PaymentConfirmResult confirm(PaymentConfirmCommand command){

        PaymentEvent paymentEvent = paymentEventRepository.findAllByOrderKey(command.getOrderId()).orElseThrow();
        List<PaymentOrder> paymentOrders = paymentEvent.getPaymentOrders();
        //PaymentOrderStatus 변경 NOT_STARTED -> EXECUTING
        updatePaymentOrderStatus(paymentOrders);

        paymentEventRepository.updatePaymentKeyByOrderId(command.getPaymentKey(), command.getOrderId());


        isAmountValid(command, paymentEvent);

        PaymentExecutionResult res = tossPaymentService.execute(command).block();
        log.info("============================================");
        log.info(res.getOrderId());

        //결제 승인 결과 따른 저장
        PaymentStatusUpdateCommand paymentStatusUpdateCommand
                = PaymentStatusUpdateCommand.builder()
                .paymentKey(res.getPaymentKey())
                .orderId(res.getOrderId())
                .status(res.paymentOrderStatus())
                .extraDetails(res.getExtraDetails())
                .failure(res.getFailure())
                .build();

        updatePaymentStatus(paymentStatusUpdateCommand);

        PaymentConfirmResult confirmResult = PaymentConfirmResult.builder()
                .status(res.paymentOrderStatus())
                .failure(res.getFailure())
                .build();
        confirmResult.isValidMessage();
        return confirmResult;
    }



    public boolean updatePaymentStatus(PaymentStatusUpdateCommand command){
        if(command.getStatus().equals(PaymentOrderStatus.SUCCESS)) {
            return updatePaymentStatusToSuccess(command);
        }else if(command.getStatus().equals(PaymentOrderStatus.FAILURE)){
            return updatePaymentStatusToFailure(command);
        }else {
            throw new IllegalArgumentException("결제 상태가 올바르지 않습니다, 상태:"+ command.getStatus());
        }

    }

    public boolean updatePaymentStatusToSuccess(PaymentStatusUpdateCommand command){
        //select paymentOrder
        //List<PaymentOrder> paymentOrderList = paymentOrderRepository.findByOrderId(command.getOrderId());
        //history 저장
//        insertPaymentHistory(paymentOrderList, command.getStatus(),"PAYMENT_CONFIRMATION_DONE");
        //update paymentOrder status
        paymentOrderRepository.updatePaymentOrderStatusByOrderId(command.getOrderId(), command.getStatus());

        String pspRawData = "";
        try {
            pspRawData = objectMapper.writeValueAsString(command.getExtraDetails().getPspRawData());
        }catch (Exception e) {
            log.error(e.getMessage());
        }

        //paymentEvent
        paymentEventRepository.updatePaymentEventExtraDetails(command.getOrderId(),
                command.getExtraDetails().getOrderName(),
                command.getExtraDetails().getMethod(),
                command.getExtraDetails().getApprovedAt(),
                command.getExtraDetails().getType(),
                pspRawData);
        return true;

    }

    public boolean updatePaymentStatusToFailure(PaymentStatusUpdateCommand command) {
//        List<PaymentOrder> paymentOrderList = paymentOrderRepository.findByOrderId(command.getOrderId());
//        insertPaymentHistory(paymentOrderList, command.getStatus(),command.getStatus().toString());
        paymentOrderRepository.updatePaymentOrderStatusByOrderId(command.getOrderId(), command.getStatus());
        return true;
    }

    private static void isAmountValid(PaymentConfirmCommand command, PaymentEvent paymentEvent) {
        int compareResult = paymentEvent.getTotalAmount().compareTo(BigDecimal.valueOf(command.getAmount()));
        if(compareResult != 0){
            throw new PaymentAmountException(ExceptionCode.PAYMENT_AMOUNT_VALID,
                    " : 결제 금액이 일치하지 않습니다. 금액: " + command.getAmount() + "원, 결제 금액: " + paymentEvent.getTotalAmount() + "원\"");
        }
    }


    public static void updatePaymentOrderStatus(List<PaymentOrder> paymentOrders) {
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
