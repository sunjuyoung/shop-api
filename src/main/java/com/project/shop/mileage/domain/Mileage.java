package com.project.shop.mileage.domain;

import com.project.shop.customer.entity.Customer;
import com.project.shop.global.domain.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Mileage extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalPoints;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    //마일리지 변동 내역
    //언제, 어떤 이유로 몇 포인트가 적립되거나 사용되었는지 추적
//    @OneToMany(mappedBy = "mileage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<MileageHistory> histories = new ArrayList<>();

}
