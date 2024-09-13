package com.project.shop.order.dto.request;

import com.project.shop.customer.vo.Address;
import com.project.shop.order.entity.ShippingInfo;
import com.project.shop.orderItem.dto.request.OrderItemDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
public class OrderRequestDTO {

    @NotNull
    private Long customerId;
    @NotNull
    private List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

    @NotBlank(message = "receiverName 빈칸입니다")
   private String receiverName;
    @NotBlank(message = "receiverPhone 빈칸입니다")
   private String receiverPhone;
    @NotBlank(message = "postCode 빈칸입니다")
   private String postCode;
    @NotBlank(message = "city 빈칸입니다")
   private String city;
    @NotBlank(message = " street 빈칸입니다")
   private String street;

}
