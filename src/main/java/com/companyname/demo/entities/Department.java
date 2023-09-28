package com.companyname.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;//default value

    //in 'department' User field we already have mapped join to departments table
    //hibernate will look at User entity because we used it as generic class for List
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "department")
    private List<User> users = new ArrayList<>();
}