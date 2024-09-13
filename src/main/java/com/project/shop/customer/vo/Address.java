package com.project.shop.customer.vo;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@Builder
@NoArgsConstructor
public class Address {

    private String postCode;
    private String city;
    private String street;

    public Address(String postCode, String city, String street) {
        this.postCode = postCode;
        this.city = city;
        this.street = street;
    }
}
