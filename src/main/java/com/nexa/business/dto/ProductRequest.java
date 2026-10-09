package com.nexa.business.dto;
import javax.validation.constraints.*;
import java.math.BigDecimal;
public class ProductRequest {
 @NotBlank @Size(max=60) private String sku;
 @NotBlank @Size(max=160) private String name;
 @Size(max=500) private String description;
 @NotNull @DecimalMin("0.0") private BigDecimal price;
 @Min(0) private int stock;
 public String getSku(){return sku;} public void setSku(String v){sku=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
 public int getStock(){return stock;} public void setStock(int v){stock=v;}
}
