package com.nexa.business.dto;
import javax.validation.constraints.*;
public class SupplierRequest {
 @NotBlank @Size(max=160) private String name;
 @Size(max=160) private String contact;
 @Size(max=50) private String phone;
 @Email @Size(max=180) private String email;
 @Size(max=120) private String city;
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getContact(){return contact;} public void setContact(String v){contact=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getCity(){return city;} public void setCity(String v){city=v;}
}
