package com.api_rest.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api_rest.auth.entity.ERole;
import com.api_rest.auth.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
	Optional<Role> findByRoleName(ERole roleName);

}
