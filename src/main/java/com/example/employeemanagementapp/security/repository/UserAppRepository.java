package com.example.employeemanagementapp.security.repository;

import com.example.employeemanagementapp.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAppRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}
