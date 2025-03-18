package com.project.shop.payment.service;

import com.project.shop.global.exception.PSPConfirmationException;
import com.project.shop.payment.domain.enums.PSPConfirmStatus;
import com.project.shop.payment.domain.enums.PaymentMethod;
import com.project.shop.payment.domain.enums.PaymentType;
import com.project.shop.payment.domain.enums.TossPaymentError;
import com.project.shop.payment.dto.request.PaymentConfirmCommand;
import com.project.shop.payment.dto.request.PaymentExecutionResult;
import com.project.shop.payment.dto.response.TossPaymentConfirmResponse;
import com.project.shop.payment.vo.Failure;
import com.project.shop.payment.vo.PaymentExtraDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossPaymentService {

    private final WebClient tossPaymentWebClient;
    private static String uri = "/v1/payments/confirm";

    public Mono<PaymentExecutionResult> execute(PaymentConfirmCommand command){
        log.info("----------TossPaymentService.execute====================");


        return tossPaymentWebClient.post()
                .uri(uri)
                .header("Idempotency-Key", command.getOrderId())
                .bodyValue(
                        """
{
                            "paymentKey": "%s",
                            "orderId": "%s",
                            "amount": "%s"
                        }
                        """.formatted(command.getPaymentKey(), command.getOrderId(), command.getAmount())

                )
                .retrieve()
                .onStatus(statusCode -> statusCode.is4xxClientError() || statusCode.is5xxServerError(), response ->
                        response.bodyToMono(Failure.class)
                                .flatMap(failure -> {
                                    TossPaymentError error = TossPaymentError.get(failure.getCode());
                                    return Mono.error(new PSPConfirmationException(error.name(), error.getMessage(),error.isRetryableError()));
                                })
                )
                .bodyToMono(TossPaymentConfirmResponse.class)
                .map(res->{
                    PaymentExtraDetails extraDetails = PaymentExtraDetails.builder()
                            .type(PaymentType.valueOf(res.getType()))
                            .method(PaymentMethod.getPaymentMethod(res.getMethod()))
                            .approvedAt(LocalDateTime.parse(res.getApprovedAt(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                            .orderName(res.getOrderName())
                            .pspConfirmStatus(PSPConfirmStatus.valueOf(res.getStatus()))
                            .totalAmount((long) res.getTotalAmount())
                            .pspRawData(res.toString())
                            .build();
                    return PaymentExecutionResult.builder()
                            .extraDetails(extraDetails)
                            .paymentKey(command.getPaymentKey())
                            .orderId(command.getOrderId())
                            .isSuccess(true)
                            .build();
                }).retryWhen(Retry.backoff(2, Duration.ofSeconds(1))
                        .jitter(0.5)
                        .filter(throwable -> throwable instanceof PSPConfirmationException&&((PSPConfirmationException) throwable).isRetryableError())
                     //   .doBeforeRetry(retrySignal -> log.info("retry count : {}", retrySignal.totalRetries()))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                // 재시도 후에도 실패하면 customException아닌 그대로 에러를 던진다.
                                retrySignal.failure()
                        )
                )
                ;
    }
}
