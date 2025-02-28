package com.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role_master")
public class RoleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;

    public RoleMaster(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public RoleMaster() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        roleName = roleName;
    }
}
