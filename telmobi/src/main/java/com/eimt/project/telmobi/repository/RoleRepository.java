package com.eimt.project.telmobi.repository;

import com.eimt.project.telmobi.model.Role;
import com.eimt.project.telmobi.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName roleName);
}
