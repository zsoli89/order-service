package hu.webuni.orderservice.service;

import hu.webuni.orderservice.model.entity.Address;
import hu.webuni.orderservice.model.entity.OrderProduct;
import hu.webuni.orderservice.model.entity.WebshopOrder;
import hu.webuni.orderservice.model.enums.OrderStatus;
import hu.webuni.orderservice.repository.AddressRepository;
import hu.webuni.orderservice.repository.OrderProductRepository;
import hu.webuni.orderservice.repository.WebshopOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitDbService {

    private final AddressRepository addressRepository;
    private final WebshopOrderRepository webshopOrderRepository;
    private final OrderProductRepository orderProductRepository;


    @Transactional
    public void deleteDb() {
        orderProductRepository.deleteAllInBatch();
        webshopOrderRepository.deleteAllInBatch();
        addressRepository.deleteAllInBatch();
    }

    @Transactional
    public void addInitData() {
        Address address1 = createAddress("4400", "Nyíregyháza", "Kossuth", "5", "2", "1B");
        Address address2 = createAddress("1152", "Budapest", "Báthory", "5", "2", "1B");
        Address address3 = createAddress("3000", "Hatvan", "Kertész", "32", null, null);

        WebshopOrder order1 = createOrder(address1, OrderStatus.PENDING, "jakab.zoltan");
        WebshopOrder order2 = createOrder(address1, OrderStatus.CONFIRMED, "jakab.zoltan");
        WebshopOrder order3 = createOrder(address2, OrderStatus.DECLINED, "jakab.zoltan");
        WebshopOrder order4 = createOrder(address3, OrderStatus.CONFIRMED, "jakab.zoltan");

        createOrderProduct(order1, 1L, 2L, 2000D, 4000L);
        createOrderProduct(order1, 2L, 3L, 2000D, 6000L);
        createOrderProduct(order1, 3L, 4L, 2000D, 8000L);
        createOrderProduct(order1, 4L, 25L, 2000D, 50000L);
        createOrderProduct(order1, 5L, 2L, 2000D, 4000L);
        createOrderProduct(order1, 6L, 2L, 2000D, 4000L);

        createOrderProduct(order2, 1L, 2L, 2000D, 4000L);
        createOrderProduct(order2, 2L, 3L, 2000D, 6000L);
        createOrderProduct(order2, 3L, 4L, 2000D, 8000L);
        createOrderProduct(order2, 4L, 25L, 2000D, 50000L);
        createOrderProduct(order2, 5L, 2L, 2000D, 4000L);
        createOrderProduct(order2, 6L, 2L, 2000D, 4000L);

        createOrderProduct(order3, 4L, 25L, 2000D, 50000L);
        createOrderProduct(order3, 5L, 2L, 2000D, 4000L);
        createOrderProduct(order3, 6L, 2L, 2000D, 4000L);

        createOrderProduct(order4, 1L, 2L, 2000D, 4000L);
        createOrderProduct(order4, 2L, 3L, 2000D, 6000L);
        createOrderProduct(order4, 3L, 4L, 2000D, 8000L);

    }

    private Address createAddress(String zip, String city, String street, String houseNr, String floor, String door) {
        return addressRepository.save(
                Address.builder()
                        .zip(zip)
                        .city(city)
                        .street(street)
                        .houseNumber(houseNr)
                        .floor(floor)
                        .door(door)
                        .build()
        );
    }

    private WebshopOrder createOrder(Address address, OrderStatus status, String username) {
        return webshopOrderRepository.save(
                WebshopOrder.builder()
                        .address(address)
                        .orderStatus(status)
                        .username(username)
                        .build()
        );
    }

    private OrderProduct createOrderProduct(WebshopOrder order, Long productId, Long quantity, Double price, Long amount) {
        return orderProductRepository.save(
                OrderProduct.builder()
                        .orderId(order)
                        .productId(productId)
                        .quantity(quantity)
                        .price(price)
                        .amount(amount)
                        .build()
        );
    }

}
