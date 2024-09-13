package com.project.shop.customer.entity.enums;

import lombok.Getter;

@Getter
public enum Grade {

    VIP(15000,7),

    GOLD(8000,4),

    SILVER(4000,2),
    BRONZE(1000,0);

    private final int point;
    private final int discountRate;


    Grade(int point, int discountRate) {
        this.point = point;
        this.discountRate = discountRate;
    }



}
