package hu.webuni.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hu.webuni.orderservice.model.dto.OrderDto;
import hu.webuni.orderservice.model.dto.WebshopOrderDto;
import hu.webuni.orderservice.model.enums.OrderStatus;
import hu.webuni.orderservice.service.WebshopOrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class WebshopOrderController {

    private final WebshopOrderService webshopOrderService;

    @GetMapping("/find/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public List<WebshopOrderDto> findByUsername(@PathVariable String username) {
        return webshopOrderService.findByUsername(username);
    }

    @PostMapping("/place-order/{username}")
    @ResponseStatus(HttpStatus.OK)
    public WebshopOrderDto placeOrder(@RequestBody OrderDto orderDto, @PathVariable String username) throws JsonProcessingException {
        MDC.put("username", username);
        return webshopOrderService.placeOrder(orderDto);
    }

    @GetMapping("/change-status/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public WebshopOrderDto changeOrderStatus(@PathVariable Long id, @PathVariable OrderStatus status) {
        return webshopOrderService.changeOrderStatus(id, status);
    }

}
