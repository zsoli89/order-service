package hu.webuni.orderservice.model.dto;

import hu.webuni.orderservice.model.entity.Address;
import hu.webuni.orderservice.model.entity.OrderProduct;
import hu.webuni.orderservice.model.enums.OrderStatus;
import lombok.Data;

import java.util.Set;

@Data
public class WebshopOrderDto {

    private Long id;
    private Address address;
    private OrderStatus orderStatus;
    private String username;
    private Set<OrderProduct> orderProducts;
}
