package com.nexa.business.config;

import com.nexa.business.domain.*;
import com.nexa.business.repository.*;
import com.nexa.business.service.AuditService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;

@Component
public class DataInitializer implements ApplicationRunner {
    private final AppUserRepository users; private final ProductRepository products; private final CustomerRepository customers;
    private final SupplierRepository suppliers; private final SaleRepository sales; private final ActivityRepository activities;
    private final PasswordEncoder encoder; private final AuditService audit;
    public DataInitializer(AppUserRepository users, ProductRepository products, CustomerRepository customers, SupplierRepository suppliers,
                           SaleRepository sales, ActivityRepository activities, PasswordEncoder encoder, AuditService audit) {
        this.users=users; this.products=products; this.customers=customers; this.suppliers=suppliers; this.sales=sales; this.activities=activities; this.encoder=encoder; this.audit=audit;
    }
    @Override @Transactional
    public void run(ApplicationArguments args) {
        seedUser("admin","admin123","Administrator","System Administrator","ROLE_ADMIN");
        seedUser("owner","owner123","Business Owner","Owner / Executive","ROLE_OWNER");
        seedUser("manager","manager123","Operations Manager","Manager Operasional","ROLE_MANAGER");
        seedUser("kasir","kasir123","Staff Kasir","Point of Sale","ROLE_CASHIER");
        seedUser("gudang","gudang123","Staff Gudang","Inventory Operations","ROLE_WAREHOUSE");
        if(products.count()==0){
            product("SKU-001","Laptop Business 14\"","Laptop kerja profesional 14 inci",new BigDecimal("12500000"),8);
            product("SKU-002","Mouse Wireless","Mouse wireless ergonomis",new BigDecimal("250000"),22);
            product("SKU-003","Keyboard Mechanical","Keyboard mechanical untuk produktivitas",new BigDecimal("850000"),12);
            product("SKU-004","Monitor 24 Inch","Monitor IPS Full HD",new BigDecimal("2200000"),4);
            product("SKU-005","USB-C Hub","Hub USB-C multiport",new BigDecimal("475000"),2);
            product("SKU-006","Headset Office","Headset meeting dengan microphone",new BigDecimal("650000"),0);
        }
        if(customers.count()==0){ customer("CUS-001","PT Nusantara Digital","081234567890","procurement@nusantara.test","Bandung","ACTIVE"); customer("CUS-002","CV Sinar Abadi","081298765432","admin@sinar.test","Jakarta","ACTIVE"); }
        if(suppliers.count()==0){ supplier("SUP-001","PT Tech Distribusi","Rina","081311112222","sales@techdist.test","Jakarta"); supplier("SUP-002","CV Periferal Jaya","Budi","081322223333","halo@periferal.test","Bandung"); }
        if(sales.count()==0){
            Product mouse=products.findBySkuIgnoreCase("SKU-002").orElse(null), keyboard=products.findBySkuIgnoreCase("SKU-003").orElse(null);
            if(mouse!=null) sale("INV-DEMO-0001",mouse,"PT Nusantara Digital",3,"Transfer","admin",Instant.now().minusSeconds(7200));
            if(keyboard!=null) sale("INV-DEMO-0002",keyboard,"Pelanggan Umum",1,"Tunai","kasir",Instant.now().minusSeconds(3600));
        }
        if(activities.count()==0) audit.log("system","INITIALIZE","SYSTEM","Nexa Business Suite v4.2.6 Presentation Ready siap digunakan");
    }
    private void seedUser(String username,String password,String display,String title,String role){if(users.findByUsernameIgnoreCase(username).isPresent())return;AppUser u=new AppUser();u.setUsername(username);u.setPasswordHash(encoder.encode(password));u.setDisplayName(display);u.setJobTitle(title);u.setRole(role);u.setEnabled(true);users.save(u);}
    private void product(String sku,String name,String desc,BigDecimal price,int stock){Product p=new Product();p.setSku(sku);p.setName(name);p.setDescription(desc);p.setPrice(price);p.setStock(stock);p.setUpdatedAt(Instant.now());products.save(p);}
    private void customer(String code,String name,String phone,String email,String city,String status){Customer c=new Customer();c.setCode(code);c.setName(name);c.setPhone(phone);c.setEmail(email);c.setCity(city);c.setStatus(status);c.setUpdatedAt(Instant.now());customers.save(c);}
    private void supplier(String code,String name,String contact,String phone,String email,String city){Supplier s=new Supplier();s.setCode(code);s.setName(name);s.setContact(contact);s.setPhone(phone);s.setEmail(email);s.setCity(city);s.setUpdatedAt(Instant.now());suppliers.save(s);}
    private void sale(String invoice,Product p,String customer,int qty,String payment,String cashier,Instant created){Sale s=new Sale();s.setInvoiceNo(invoice);s.setProductId(p.getId());s.setProductName(p.getName());s.setCustomerName(customer);s.setQuantity(qty);s.setUnitPrice(p.getPrice());s.setTotal(p.getPrice().multiply(BigDecimal.valueOf(qty)));s.setPaymentMethod(payment);s.setCashier(cashier);s.setStatus("PAID");s.setCreatedAt(created);sales.save(s);}
}
