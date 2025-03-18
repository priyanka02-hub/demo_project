package com.project.repo;

import com.project.model.RoleMaster;
import com.project.model.UserMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<RoleMaster, Long> {
    @Query("SELECT r FROM RoleMaster r WHERE r.roleName = :roleName")
    Optional<RoleMaster> findByRoleName(String roleName);

    //Page<RoleMaster> findAll(Pageable pageable);
}

