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
public interface OrderProductMapper {

    OrderProduct dtoToEntity(OrderProductDto dto);
    OrderProductDto entityToDto(OrderProduct entity);

    List<OrderProduct> dtoToProductList(List<OrderProductDto> dtoList);

    @IterableMapping(qualifiedByName = "summary")
    List<OrderProduct> dtoSummariesToProductList(List<OrderProductDto> dtoList);

    @Named("summary")
    @Mapping(target = "orderId", ignore = true)
    OrderProduct productToDto(OrderProductDto entity);

    @Mapping(target = "orderProducts", ignore = true)
    WebshopOrder dtoToWebshop(WebshopOrderDto dto);

    @Mapping(target = "orderProducts", ignore = true)
    WebshopOrderDto webshopToDto(WebshopOrder dto);
}
