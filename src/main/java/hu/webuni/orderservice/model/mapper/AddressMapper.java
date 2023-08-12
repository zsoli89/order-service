package hu.webuni.orderservice.model.mapper;

import hu.webuni.orderservice.model.dto.AddressDto;
import hu.webuni.orderservice.model.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto entityToDto(Address entity);
    Address dtoToEntity(AddressDto dto);
}
