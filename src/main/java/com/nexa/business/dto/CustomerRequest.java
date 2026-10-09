package com.nexa.business.dto;
import javax.validation.constraints.*;
public class CustomerRequest {
 @NotBlank @Size(max=160) private String name;
 @Size(max=50) private String phone;
 @Email @Size(max=180) private String email;
 @Size(max=120) private String city;
 @Size(max=30) private String status;
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getCity(){return city;} public void setCity(String v){city=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
}
