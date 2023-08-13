package hu.webuni.orderservice.model.dto;

import hu.webuni.orderservice.model.enums.OrderStatus;
import lombok.Data;

import java.util.Set;

@Data
public class WebshopOrderDto {

    private Long id;
    private AddressDto address;
    private OrderStatus orderStatus;
    private String username;
    private Set<OrderProductDto> orderProducts;
}
