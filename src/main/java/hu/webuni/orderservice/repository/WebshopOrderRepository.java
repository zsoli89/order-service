package hu.webuni.orderservice.repository;

import hu.webuni.orderservice.model.entity.WebshopOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WebshopOrderRepository extends JpaRepository<WebshopOrder, Long> {

    @EntityGraph(attributePaths = {"orderProducts"})
    @Query("SELECT w FROM WebshopOrder w WHERE w.username=?1")
    List<WebshopOrder> findByUsername(String username);

    @EntityGraph(attributePaths = {"address"})
    @Query("SELECT w FROM WebshopOrder w WHERE w.username=?1")
    List<WebshopOrder> findAllWithAddress(String username);

    @EntityGraph(attributePaths = {"orderProducts"})
    @Query("SELECT w FROM WebshopOrder w WHERE w.id IN :ids")
    List<WebshopOrder> findByIdWithProducts(@Param("ids") List<Long> ids);

}
