package hu.webuni.orderservice.repository;

import hu.webuni.orderservice.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.webshopAddress='BOOLEAN_TRUE'")
    List<Address> findAddressByWebshopAddress();
}
