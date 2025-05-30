package com.mr.deanshop.auth.repository;

import com.mr.deanshop.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDetailRepository extends JpaRepository<User, UUID> {
    User findByEmail(String username);
}
