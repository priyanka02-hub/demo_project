package com.project.repo;

import com.project.model.UserMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserMaster, Long> {
    UserMaster findByEmail(String email);
    Page<UserMaster> findAll(Pageable pageable);
    @Query("SELECT u FROM UserMaster u WHERE u.role.roleName = :roleName")
    Page<UserMaster> findByRoleRoleName(@Param("roleName") String roleName, Pageable pageable);
    
}
