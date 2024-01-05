package com.example.employeemanagementapp.security.repository;

import com.example.employeemanagementapp.security.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole , String> {
}
