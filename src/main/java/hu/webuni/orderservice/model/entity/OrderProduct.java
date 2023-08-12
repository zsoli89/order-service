package hu.webuni.orderservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Long id;
    @ManyToOne
    private WebshopOrder orderId;
    private Long productId;
    private Long quantity;
    private Long amount;

    private Double price;
    private String brand;
    private String productName;
    private Long productPcsQuantity;
    private Long size;
    private String amountUnits;
    private String description;
    private String color;
}
