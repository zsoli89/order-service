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
public class WebshopOrder {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Long id;
    @ManyToOne
    private Address address;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String username;
    @OneToMany(mappedBy = "orderId")
    private Set<OrderProduct> orderProducts;
    private String shippingId;
}
