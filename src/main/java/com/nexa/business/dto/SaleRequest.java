package com.nexa.business.dto;
import javax.validation.constraints.*;
public class SaleRequest {
 @NotNull private Long productId;
 @NotBlank @Size(max=160) private String customerName;
 @Min(1) private int quantity;
 @NotBlank @Size(max=40) private String paymentMethod;
 public Long getProductId(){return productId;} public void setProductId(Long v){productId=v;}
 public String getCustomerName(){return customerName;} public void setCustomerName(String v){customerName=v;}
 public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
 public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;}
}
