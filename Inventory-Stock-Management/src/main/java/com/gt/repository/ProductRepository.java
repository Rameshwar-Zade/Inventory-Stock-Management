package com.gt.repository;

import com.gt.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByDealerId(Long dealerId);

    // (Ticket 14)
    @Query("SELECT p FROM Product p WHERE p.quantity < p.minStockLevel")
    List<Product> findLowStockProducts();
}
