package hu.webuni.orderservice.model.dto;

import java.util.Map;

public class OrderRequestDto {

    private Long addressId;
    private Map<Long, Long> products;
}
