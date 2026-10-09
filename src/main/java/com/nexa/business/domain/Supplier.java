package com.nexa.business.domain;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="supplier")
public class Supplier {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=40) private String code;
    @Column(nullable=false, length=160) private String name;
    @Column(length=160) private String contact;
    @Column(length=50) private String phone;
    @Column(length=180) private String email;
    @Column(length=120) private String city;
    @Column(name="updated_at", nullable=false) private Instant updatedAt = Instant.now();
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getCode(){return code;} public void setCode(String v){code=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getContact(){return contact;} public void setContact(String v){contact=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getCity(){return city;} public void setCity(String v){city=v;}
    public Instant getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Instant v){updatedAt=v;}
}
