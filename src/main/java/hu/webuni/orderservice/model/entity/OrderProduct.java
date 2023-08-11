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
    private Order orderId;
    private Long productId;
    private Long quantity;
    private Double price;
    private Double amount;
}
