package com.companyname.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    //since we use wrapper class values can be null as defined in db.
    //if we used example int default would be 0!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String username;

    private String password;

    private Integer age;

    @Column(name = "is_active")
    private Boolean isActive = false;//default value

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_department")
//    @JsonIgnoreProperties({"users"})
    private Department department;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "fk_user"),
            inverseJoinColumns = @JoinColumn(name = "fk_role")
    )
    private List<Role> roles = new ArrayList<>();

    public void addRole(@NonNull Role role) {
        roles.add(role);
    }

    public void removeRole(@NonNull Integer id) {
        roles.removeIf(role -> role.getId().equals(id));
    }
}