package hu.webuni.orderservice.model.dto;

import hu.webuni.orderservice.model.entity.Address;
import hu.webuni.orderservice.model.entity.OrderProduct;
import hu.webuni.orderservice.model.enums.OrderStatus;

import java.util.Set;

public class OrderDto {

    private Long id;
    private Address address;
    private OrderStatus orderStatus;
    private Set<OrderProduct> orderProducts;
}
