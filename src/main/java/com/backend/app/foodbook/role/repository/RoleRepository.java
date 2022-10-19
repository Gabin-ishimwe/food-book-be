package com.backend.app.foodbook.role.repository;

import com.backend.app.foodbook.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
