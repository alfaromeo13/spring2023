package com.companyname.demo.filters;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductSearchFilter {
    private String title;
    private String shortDescription;
    private String longDescription;
    private BigDecimal price;
    private Date createdAt;
    private String categoryName;
}
