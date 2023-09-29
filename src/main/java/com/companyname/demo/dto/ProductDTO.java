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
        private Date createdAt;
        private Boolean isActive;
        private CategoryDTO category;
}
