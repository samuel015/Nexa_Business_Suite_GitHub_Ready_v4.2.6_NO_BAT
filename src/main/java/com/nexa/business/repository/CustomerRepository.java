package com.nexa.business.repository;
import com.nexa.business.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer,Long>{
    Page<Customer> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(String name,String code,Pageable pageable);
}
