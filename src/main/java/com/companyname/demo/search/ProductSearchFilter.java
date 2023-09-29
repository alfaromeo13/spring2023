package com.companyname.demo.search;


import lombok.Data;
import org.springframework.stereotype.Component;

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
