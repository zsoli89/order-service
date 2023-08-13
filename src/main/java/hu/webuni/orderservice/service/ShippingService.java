package hu.webuni.orderservice.service;

import hu.webuni.jms.OrderRequest;
import jakarta.jms.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingService {

    public static final String ORDER_STATUS = "orderstatus";
    private final JmsTemplate shippingTemplate;

    public void entrustDeliveryOrder(Long orderId, String shippingAddress, List<String> productList, String pickingUpAddress) {
        Topic responseTopic = shippingTemplate.execute(session ->
                session.createTopic(ORDER_STATUS));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(orderId);
        orderRequest.setShippingAddress(shippingAddress);
        orderRequest.setProducts(productList);
        orderRequest.setPickingUpAddress(pickingUpAddress);
        shippingTemplate.convertAndSend("shippingstatus", orderRequest, message -> {
            message.setJMSReplyTo(responseTopic);
            return message;
        });
    }
}
