package com.nexa.business.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="sale")
public class Sale {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="invoice_no", nullable=false, unique=true, length=70) private String invoiceNo;
    @Column(name="product_id", nullable=false) private Long productId;
    @Column(name="product_name", nullable=false, length=160) private String productName;
    @Column(name="customer_name", nullable=false, length=160) private String customerName;
    @Column(nullable=false) private int quantity;
    @Column(name="unit_price", nullable=false, precision=19, scale=2) private BigDecimal unitPrice;
    @Column(nullable=false, precision=19, scale=2) private BigDecimal total;
    @Column(name="payment_method", nullable=false, length=40) private String paymentMethod;
    @Column(nullable=false, length=50) private String cashier;
    @Column(nullable=false, length=30) private String status = "PAID";
    @Column(name="created_at", nullable=false) private Instant createdAt = Instant.now();
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getInvoiceNo(){return invoiceNo;} public void setInvoiceNo(String v){invoiceNo=v;}
    public Long getProductId(){return productId;} public void setProductId(Long v){productId=v;}
    public String getProductName(){return productName;} public void setProductName(String v){productName=v;}
    public String getCustomerName(){return customerName;} public void setCustomerName(String v){customerName=v;}
    public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
    public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal v){unitPrice=v;}
    public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal v){total=v;}
    public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;}
    public String getCashier(){return cashier;} public void setCashier(String v){cashier=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}
