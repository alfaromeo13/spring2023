package com.companyname.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@DynamicUpdate
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @ToString.Exclude
    @JsonManagedReference //with this below we allow all cascade operations
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product != null) this.products.add(product);
    }

    public void removeProduct(Integer productId) {
        products.removeIf(product -> product.getId().equals(productId));
    }
}