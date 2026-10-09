package com.nexa.business.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="customer")
public class Customer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=40) private String code;
    @Column(nullable=false, length=160) private String name;
    @Column(length=50) private String phone;
    @Column(length=180) private String email;
    @Column(length=120) private String city;
    @Column(nullable=false, length=30) private String status = "ACTIVE";
    @Column(name="updated_at", nullable=false) private Instant updatedAt = Instant.now();
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getCode(){return code;} public void setCode(String v){code=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getCity(){return city;} public void setCity(String v){city=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public Instant getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Instant v){updatedAt=v;}
}
