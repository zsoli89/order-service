package hu.webuni.orderservice.model.mapper;

import hu.webuni.orderservice.model.dto.OrderProductDto;
import hu.webuni.orderservice.model.entity.OrderProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    OrderProduct dtoToEntity(OrderProductDto dto);
    OrderProductDto entityToDto(OrderProduct entity);
}
