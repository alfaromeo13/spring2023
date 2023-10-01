package com.companyname.demo.entities;

import com.companyname.demo.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @JoinColumn(name = "fk_customer")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    //add one item
    public void addItem(@NonNull OrderItem item) {
        items.add(item);
    }

    //add list of items
    public void addItem(@NonNull List<OrderItem> item) {
        items.addAll(item);
    }
}