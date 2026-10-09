package com.nexa.business.repository;
import com.nexa.business.domain.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SupplierRepository extends JpaRepository<Supplier,Long>{
    Page<Supplier> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(String name,String code,Pageable pageable);
}
