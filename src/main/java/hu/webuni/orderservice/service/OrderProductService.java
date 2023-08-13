package hu.webuni.orderservice.service;

import hu.webuni.orderservice.model.dto.OrderProductDto;
import hu.webuni.orderservice.model.entity.OrderProduct;
import hu.webuni.orderservice.model.mapper.OrderProductMapper;
import hu.webuni.orderservice.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private static final Logger logger = LoggerFactory.getLogger(OrderProductService.class);

    private final OrderProductRepository orderProductRepository;
    private final OrderProductMapper orderProductMapper;

    @Transactional
    public List<OrderProductDto> createAll(List<OrderProduct> orderProductList) {
        orderProductList.forEach(o -> {
            Long amount = (long) (o.getPrice() * o.getQuantity());
            o.setAmount(amount);
        });
        logger.info("Order Product list size: {}", orderProductList.size());
        List<OrderProduct> savedList = orderProductRepository.saveAll(orderProductList);
        logger.info("Order Product entities saved in repository.");
        return savedList.stream().map(orderProductMapper::entityToDto).toList();
    }
}
