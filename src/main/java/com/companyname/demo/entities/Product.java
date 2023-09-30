package com.companyname.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    private BigDecimal price;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "is_active")
    private Boolean isActive = Boolean.FALSE;

    @ToString.Exclude
    @JoinColumn(name = "fk_category")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}