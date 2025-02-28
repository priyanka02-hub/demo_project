package com.project.model;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="user_master")
public class UserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double salary;
    private String designation;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleMaster role;


    public UserMaster() {
    }



    public UserMaster(Long id, String name, double salary, String designation, String email, String password, RoleMaster role) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.designation = designation;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleMaster getRole() {
        return role;
    }

    public void setRole(RoleMaster role) {
        this.role = role;
    }
}
