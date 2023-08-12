package hu.webuni.orderservice.model.mapper;

import hu.webuni.orderservice.model.dto.WebshopOrderDto;
import hu.webuni.orderservice.model.entity.WebshopOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WebshopOrderMapper {

    WebshopOrder dtoToEntity(WebshopOrderDto dto);
    WebshopOrderDto entityToDto(WebshopOrder entity);
}
