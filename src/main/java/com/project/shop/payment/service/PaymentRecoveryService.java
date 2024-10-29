package com.project.shop.payment.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class PaymentRecoveryService {

   //@Scheduled(fixedDelay = 180, timeUnit = TimeUnit.SECONDS)
    public void recovery(){

    }
}
