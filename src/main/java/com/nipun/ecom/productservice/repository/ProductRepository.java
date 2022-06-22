package com.nipun.ecom.productservice.repository;

import com.nipun.ecom.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Nipun on 3/6/22
 */

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<List<Product>> findByNameLikeIgnoreCase(String name);

    Optional<Product> findByProductCode(String productCode);

    @Modifying
    @Query("update Product p set p.stockQuantity = stockQuantity - ?2 where p.productCode = ?1")
    Integer updateByProductCode(String productCode, Integer soldCount);

    @Query("select p.price from Product p where p.productCode = ?1 ")
    BigDecimal findUnitPriceByProductCode(String productCode);
}
