package com.macielzeferino.authenticationsystem.repository;

import com.macielzeferino.authenticationsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
   UserDetails findByUserLogin(String userLogin);
}
