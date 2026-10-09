package com.nexa.business.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="product")
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=60) private String sku;
    @Column(nullable=false, length=160) private String name;
    @Column(length=500) private String description;
    @Column(nullable=false, precision=19, scale=2) private BigDecimal price;
    @Column(nullable=false) private int stock;
    @Column(name="updated_at", nullable=false) private Instant updatedAt = Instant.now();

    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getSku(){return sku;} public void setSku(String v){sku=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
    public int getStock(){return stock;} public void setStock(int v){stock=v;}
    public Instant getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Instant v){updatedAt=v;}
}
