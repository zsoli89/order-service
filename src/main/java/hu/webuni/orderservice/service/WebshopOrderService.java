package hu.webuni.orderservice.service;

import hu.webuni.orderservice.model.dto.OrderRequestDto;
import hu.webuni.orderservice.model.dto.WebshopOrderDto;
import hu.webuni.orderservice.model.entity.WebshopOrder;
import hu.webuni.orderservice.model.mapper.WebshopOrderMapper;
import hu.webuni.orderservice.repository.WebshopOrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebshopOrderService {

    private static final Logger logger = LoggerFactory.getLogger(WebshopOrderService.class);

    private final WebshopOrderRepository webshopOrderRepository;
    private final WebshopOrderMapper webshopOrderMapper;

    public List<WebshopOrderDto> findByUsername(String username) {
        List<WebshopOrder> orderByUsernameDtoList = webshopOrderRepository.findByUsername(username);
        logger.info("{} Webshop Order entity found by username: {}", orderByUsernameDtoList.size(), username);
        return webshopOrderMapper.webshopOrderListToDtoList(orderByUsernameDtoList);
    }


    public List<WebshopOrderDto> placeOrder(OrderRequestDto orderRequestDto) {

        return null;
    }
}
