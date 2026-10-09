package com.nexa.business.web;

import com.nexa.business.domain.*;
import com.nexa.business.dto.*;
import com.nexa.business.service.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BusinessApiController {
    private final BusinessService business; private final AuditService audit;
    public BusinessApiController(BusinessService b,AuditService a){business=b;audit=a;}
    @GetMapping("/dashboard") public Map<String,Object> dashboard(){return business.dashboard();}
    @GetMapping("/notifications") public Map<String,Object> notifications(){return business.notifications();}
    @GetMapping("/reports") @PreAuthorize("hasAnyRole('ADMIN','OWNER','MANAGER')") public Map<String,Object> reports(){return business.reports();}
    @GetMapping("/activity") @PreAuthorize("hasAnyRole('ADMIN','OWNER','MANAGER')") public Page<Activity> activity(){return audit.latest(100);}

    @GetMapping("/products") public Page<Product> products(@RequestParam(defaultValue="") String search,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="50") int size){return business.products(search,page,size);}
    @PostMapping("/products") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public Product createProduct(@Valid @RequestBody ProductRequest r,Authentication a){return business.createProduct(r,a);}
    @PutMapping("/products/{id}") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public Product updateProduct(@PathVariable long id,@Valid @RequestBody ProductRequest r,Authentication a){return business.updateProduct(id,r,a);}
    @DeleteMapping("/products/{id}") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public ResponseEntity<Void> deleteProduct(@PathVariable long id,Authentication a){business.deleteProduct(id,a);return ResponseEntity.noContent().build();}

    @GetMapping("/customers") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public Page<Customer> customers(@RequestParam(defaultValue="") String search){return business.customers(search);}
    @PostMapping("/customers") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public Customer createCustomer(@Valid @RequestBody CustomerRequest r,Authentication a){return business.createCustomer(r,a);}
    @PutMapping("/customers/{id}") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public Customer updateCustomer(@PathVariable long id,@Valid @RequestBody CustomerRequest r,Authentication a){return business.updateCustomer(id,r,a);}
    @DeleteMapping("/customers/{id}") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public ResponseEntity<Void> deleteCustomer(@PathVariable long id,Authentication a){business.deleteCustomer(id,a);return ResponseEntity.noContent().build();}

    @GetMapping("/suppliers") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public Page<Supplier> suppliers(@RequestParam(defaultValue="") String search){return business.suppliers(search);}
    @PostMapping("/suppliers") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public Supplier createSupplier(@Valid @RequestBody SupplierRequest r,Authentication a){return business.createSupplier(r,a);}
    @PutMapping("/suppliers/{id}") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public Supplier updateSupplier(@PathVariable long id,@Valid @RequestBody SupplierRequest r,Authentication a){return business.updateSupplier(id,r,a);}
    @DeleteMapping("/suppliers/{id}") @PreAuthorize("hasAnyRole('ADMIN','MANAGER')") public ResponseEntity<Void> deleteSupplier(@PathVariable long id,Authentication a){business.deleteSupplier(id,a);return ResponseEntity.noContent().build();}

    @GetMapping("/sales") public Page<Sale> sales(){return business.sales();}
    @PostMapping("/sales") @PreAuthorize("hasAnyRole('ADMIN','MANAGER','CASHIER')") public Sale createSale(@Valid @RequestBody SaleRequest r,Authentication a){return business.createSale(r,a);}
}
