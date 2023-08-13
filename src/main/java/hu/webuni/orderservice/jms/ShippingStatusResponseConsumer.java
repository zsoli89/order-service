package hu.webuni.orderservice.jms;

import hu.webuni.jms.OrderResponse;
import hu.webuni.orderservice.service.ShippingService;
import hu.webuni.orderservice.service.WebshopOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShippingStatusResponseConsumer {

    private final WebshopOrderService webshopOrderService;

    @JmsListener(destination = ShippingService.ORDER_STATUS,
            containerFactory = "shippingFactory")
    public void shippingStatusMessage(OrderResponse response) {
        webshopOrderService.updateShippingStatus(response.getOrderId(), response.getOrderStatus(), response.getShippingId());
    }
}
