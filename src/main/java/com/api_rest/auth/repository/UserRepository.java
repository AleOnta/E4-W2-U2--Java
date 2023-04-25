package com.api_rest.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api_rest.auth.entity.UserAuth;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findByEmail(String email);

    Optional<UserAuth> findByUsernameOrEmail(String username, String email);

    Optional<UserAuth> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
