package com.project.shop.order.controller;

import com.project.shop.order.dto.request.OrderRequestDTO;
import com.project.shop.order.dto.response.OrderViewResponseDTO;
import com.project.shop.order.facade.OptimisticLockProduct;
import com.project.shop.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderViewResponseDTO> order(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        OrderViewResponseDTO orderViewResponseDTO = orderService.order(orderRequestDTO);
        return ResponseEntity.ok().body(orderViewResponseDTO);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> order(@PathVariable("orderId")Long orderId){
     orderService.cancelOrder(orderId);
        return null;
    }



}
