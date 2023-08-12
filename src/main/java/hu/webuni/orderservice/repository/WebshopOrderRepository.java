package hu.webuni.orderservice.repository;

import hu.webuni.orderservice.model.entity.WebshopOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WebshopOrderRepository extends JpaRepository<WebshopOrder, Long> {

    @EntityGraph(attributePaths = {"orderProducts"})
    @Query("SELECT w FROM WebshopOrder w WHERE w.username=?1")
    List<WebshopOrder> findByUsername(String username);
}
