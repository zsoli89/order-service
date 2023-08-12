package hu.webuni.orderservice.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class OrderRequestDto {

    private Long addressId;
    private Map<Long, Long> products;


}
