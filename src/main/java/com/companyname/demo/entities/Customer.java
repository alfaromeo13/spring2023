package com.companyname.demo.entities;

import com.companyname.demo.enumerations.CustomerTypeEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    //in this way we allow only these 2 string values to be inserted
    @Enumerated(EnumType.STRING)
    private CustomerTypeEnum type;

    @Column(name = "is_vip")
    private Boolean isVip = Boolean.FALSE;

    @Column(name = "joined_at")
    private Date joinedAt;
}