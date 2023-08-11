package hu.webuni.orderservice.model.dto;

import hu.webuni.orderservice.model.entity.Order;

public class OrderProductDto {

    private Long id;
    private Order orderId;
    private Long productId;
    private Long quantity;
    private Double price;
    private Double amount;

}
