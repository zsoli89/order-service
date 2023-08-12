package hu.webuni.orderservice.controller;

import hu.webuni.orderservice.model.dto.OrderRequestDto;
import hu.webuni.orderservice.model.dto.WebshopOrderDto;
import hu.webuni.orderservice.service.WebshopOrderService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/place-order")
    @ResponseStatus(HttpStatus.OK)
    public List<WebshopOrderDto> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return webshopOrderService.placeOrder(orderRequestDto);
    }

}
