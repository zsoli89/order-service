package hu.webuni.orderservice.model.mapper;

import hu.webuni.orderservice.model.dto.WebshopOrderDto;
import hu.webuni.orderservice.model.entity.WebshopOrder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WebshopOrderMapper {

    WebshopOrder dtoToEntity(WebshopOrderDto dto);
    WebshopOrderDto entityToDto(WebshopOrder entity);
    List<WebshopOrderDto> webshopOrderListToDtoList(List<WebshopOrder> webshopOrderList);
}
