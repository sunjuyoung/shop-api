package com.project.shop.payment.controller;

import com.project.shop.payment.dto.request.CheckoutRequest;
import com.project.shop.payment.dto.request.PaymentConfirmCommand;
import com.project.shop.payment.dto.request.TossPaymentConfirmRequest;
import com.project.shop.payment.dto.response.ApiResponse;
import com.project.shop.payment.dto.response.PaymentConfirmResult;
import com.project.shop.payment.service.CheckoutService;
import com.project.shop.payment.service.PaymentConfirmFacade;
import com.project.shop.payment.service.PaymentConfirmService;
import com.project.shop.payment.service.TossPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final CheckoutService checkoutService;

  //  private final PaymentConfirmService confirmService;

    private final PaymentConfirmFacade paymentConfirmFacade;


    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<Long>> checkout(@RequestBody CheckoutRequest checkoutRequest){
        Long checkout = checkoutService.checkout(checkoutRequest);
        return ResponseEntity.ok().body(new ApiResponse<>("success", HttpStatus.OK, checkout));
    }



    @RequestMapping("/confirm")
     public ResponseEntity<ApiResponse<PaymentConfirmResult>> confirmPayment(@RequestBody TossPaymentConfirmRequest request) {
      PaymentConfirmCommand paymentConfirmCommand = new PaymentConfirmCommand(
               request.getPaymentKey(),
               request.getOrderId(),
               request.getAmount()
       );

        //PaymentConfirmResult confirm = confirmService.confirm(paymentConfirmCommand);
        PaymentConfirmResult confirm = paymentConfirmFacade.confirmPayment(paymentConfirmCommand);


        return ResponseEntity.ok().body(new ApiResponse<>("success", HttpStatus.OK, confirm));
    }





}
