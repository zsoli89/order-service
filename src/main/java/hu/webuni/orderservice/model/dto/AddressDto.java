package hu.webuni.orderservice.model.dto;

import lombok.Data;

@Data
public class AddressDto {

    private Long id;
    private String zip;
    private String city;
    private String street;
    private String houseNumber;
    private String floor;
    private String door;
    private String webshopAddress;
}
