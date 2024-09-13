package com.project.shop.order.service;

import com.project.shop.customer.entity.Customer;
import com.project.shop.customer.repository.CustomerRepository;
import com.project.shop.customer.vo.Address;
import com.project.shop.global.exception.ProductNotFoundException;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.order.dto.request.OrderRequestDTO;
import com.project.shop.order.dto.response.OrderViewResponseDTO;
import com.project.shop.order.entity.Order;
import com.project.shop.order.entity.ShippingInfo;
import com.project.shop.order.entity.enums.DeliveryStatus;
import com.project.shop.order.entity.enums.OrderState;
import com.project.shop.order.facade.RedissonLockProduct;
import com.project.shop.order.repository.OrderRepository;
import com.project.shop.orderItem.dto.request.OrderItemDTO;
import com.project.shop.orderItem.entity.OrderItem;
import com.project.shop.product.entity.Product;
import com.project.shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final RedissonLockProduct redissonLockProduct;


    public OrderViewResponseDTO order(OrderRequestDTO orderRequestDTO){
        List<OrderItemDTO> orderItemDTOS = orderRequestDTO.getOrderItemDTOS();

        Customer customer = customerRepository.findById(orderRequestDTO.getCustomerId()).orElseThrow();

        Address address = new Address(
                orderRequestDTO.getPostCode(),
                orderRequestDTO.getCity(),
                orderRequestDTO.getStreet()
        );
        ShippingInfo shippingInfo = new ShippingInfo(
                orderRequestDTO.getReceiverName(),
                orderRequestDTO.getReceiverPhone(),
                address
        );


        Order order = new Order(
                customer,
                shippingInfo,
                OrderState.PROCESSING,
                DeliveryStatus.PENDING
        );
        OrderViewResponseDTO orderViewResponseDTO = new OrderViewResponseDTO();


        orderItemDTOS.forEach(orderItemDTO -> {
            Product product = productRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(ExceptionCode.NOT_FOUND_PRODUCT));
            orderViewResponseDTO.setOrderName(product.getName());

            redissonLockProduct.decrease(product,orderItemDTO.getQuantity());

            OrderItem orderItem = OrderItem.createOrderItem(product, orderItemDTO.getQuantity());
            orderItem.setAmounts();
            order.setOrderItems(orderItem);
        });
        order.setTotalPrice();

        orderViewResponseDTO.setAmount(order.getTotalPrice());


        if(orderItemDTOS.size() > 1){
            orderViewResponseDTO.setOrderName(orderViewResponseDTO.getOrderName()+" 그 외 "+orderItemDTOS.size()+"건");
        }


        Order newOrder = orderRepository.save(order);
        orderViewResponseDTO.setOrderId(newOrder.getId());
        return orderViewResponseDTO;
    }


    public Long cancelOrder(Long orderId){
        Order order = orderRepository.findAllAndOrderItemsById(orderId).orElseThrow();
        order.cancel();
        orderRepository.save(order);
        return order.getId();
    }




}
