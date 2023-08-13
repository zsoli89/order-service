package hu.webuni.orderservice.model.dto;

import lombok.Data;

@Data
public class OrderProductDto {

    private Long id;
    private WebshopOrderDto orderId;
    private Long productId;
    private Long quantity;
    private Double price;
    private Long amount;

    private String brand;
    private String productName;
    private Long productPcsQuantity;
    private Long size;
    private String amountUnits;
    private String description;
    private String color;

}
