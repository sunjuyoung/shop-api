package com.project.shop.order.entity;

import com.project.shop.customer.vo.Address;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ShippingInfo {

    private String receiverName;
    private String receiverPhone;
    private Address address;

    public ShippingInfo(String receiverName, String receiverPhone, Address address) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.address = address;
    }
}
