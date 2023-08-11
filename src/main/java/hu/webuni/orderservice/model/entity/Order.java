package hu.webuni.orderservice.model.entity;

import hu.webuni.orderservice.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Order {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Long id;
    @OneToOne
    private Address address;
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "orderId")
    private Set<OrderProduct> orderProducts;

}
