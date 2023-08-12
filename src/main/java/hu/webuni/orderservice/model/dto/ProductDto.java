package hu.webuni.orderservice.model.dto;

import hu.webuni.commonlib.base.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDto extends BaseDto {

    private Long id;
    private String brand;
    private String name;
    private Double price;
    private Long quantity;
    private Long size;
    private String amountUnits;
    private String description;
    private String color;
    private Long categoryId;
}
