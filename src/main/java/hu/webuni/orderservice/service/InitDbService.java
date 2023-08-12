package hu.webuni.orderservice.service;

import hu.webuni.orderservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitDbService {

    private final AddressRepository addressRepository;
    private final Orde addressRepository;
    private final AddressRepository addressRepository;


    @Transactional
    public void deleteDb() {
        addressRepository.deleteAllInBatch();
    }
}
