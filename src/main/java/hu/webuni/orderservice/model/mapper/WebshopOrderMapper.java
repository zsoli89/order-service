package hu.webuni.orderservice.model.mapper;

import hu.webuni.orderservice.model.dto.OrderProductDto;
import hu.webuni.orderservice.model.dto.WebshopOrderDto;
import hu.webuni.orderservice.model.entity.OrderProduct;
import hu.webuni.orderservice.model.entity.WebshopOrder;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WebshopOrderMapper {

    WebshopOrder dtoToEntity(WebshopOrderDto dto);
    WebshopOrderDto entityToDto(WebshopOrder entity);

    List<WebshopOrderDto> webshopOrderListToDtoList(List<WebshopOrder> webshopOrderList);
    @IterableMapping(qualifiedByName = "summary")
    List<WebshopOrderDto> webshopOrderSummariestToDtoList(List<WebshopOrder> webshopOrderList);

    @Named("summary")
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "orderProducts", ignore = true)
    WebshopOrderDto webshopOrderToDto(WebshopOrder entity);

    @Mapping(target = "orderId", ignore = true)
    OrderProductDto orderToDto(OrderProduct orderProduct);
}
