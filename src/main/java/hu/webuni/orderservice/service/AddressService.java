package hu.webuni.orderservice.service;


import hu.webuni.orderservice.model.entity.Address;
import hu.webuni.orderservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;

    public Address findById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            logger.error("Couldn't find Address entity by id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return address.get();
    }

    public Address findWebshopAdress() {
        List<Address> addressByWebshopAddress = addressRepository.findAddressByWebshopAddress();
        if (addressByWebshopAddress.size() > 1) {
            logger.error("Database incosistency. Webshop address found with list size: {}", addressByWebshopAddress.size());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Webshop Address entity found with id: {}", addressByWebshopAddress.get(0).getId());
        return addressByWebshopAddress.get(0);
    }

}
