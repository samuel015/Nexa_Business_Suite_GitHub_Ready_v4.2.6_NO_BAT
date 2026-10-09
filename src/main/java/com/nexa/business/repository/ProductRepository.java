package com.nexa.business.repository;
import com.nexa.business.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ProductRepository extends JpaRepository<Product,Long>{
    Optional<Product> findBySkuIgnoreCase(String sku);
    Page<Product> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(String name,String sku,Pageable pageable);
}
