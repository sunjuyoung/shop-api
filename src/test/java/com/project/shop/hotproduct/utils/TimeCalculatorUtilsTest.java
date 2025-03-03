package com.project.shop.hotproduct.utils;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
class TimeCalculatorUtilsTest {


    @Test
    void test(){

        Duration duration = TimeCalculatorUtils.calculateDurationToMidnight();

        System.out.println("duration "+ duration.getSeconds() / 60);


    }

}