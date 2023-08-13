package hu.webuni.orderservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Address {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Long id;
    private String zip;
    private String city;
    private String street;
    private String houseNumber;
    private String floor;
    private String door;
    private String webshopAddress;
}
