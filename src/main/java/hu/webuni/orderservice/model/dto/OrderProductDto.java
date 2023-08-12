package hu.webuni.orderservice.model.dto;

import hu.webuni.orderservice.model.entity.WebshopOrder;

import java.util.List;

public class OrderProductDto {

    private Long id;
    private WebshopOrder orderId;
    private Long productId;
    private Long quantity;
    private Double price;
    private Long amount;
    private List<ProductDto> productDtoList;

}
