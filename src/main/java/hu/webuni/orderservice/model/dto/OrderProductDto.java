package hu.webuni.orderservice.model.dto;

import hu.webuni.orderservice.model.entity.WebshopOrder;
import lombok.Data;

@Data
public class OrderProductDto {

    private Long id;
    private WebshopOrder orderId;
    private Long productId;
    private Long quantity;
    private Double price;
    private Long amount;

    private String brand;
    private String name;
    private Long productPcsQuantity;
    private Long size;
    private String amountUnits;
    private String description;
    private String color;

}
