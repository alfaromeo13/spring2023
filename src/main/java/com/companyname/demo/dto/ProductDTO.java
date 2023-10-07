package com.companyname.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDTO {
    private Integer id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private BigDecimal price;
    private Date createdAt = new Date();
    //isActive's default value is false.If we pass true it will be true
    private Boolean isActive = Boolean.FALSE;
    //we also just pass id of category(there is no need for whole object)
    private CategoryDTO category;
}