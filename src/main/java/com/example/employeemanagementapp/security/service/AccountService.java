package com.example.employeemanagementapp.security.service;

import com.example.employeemanagementapp.security.entities.AppUser;
import com.example.employeemanagementapp.security.entities.UserRole;

public interface AccountService {
    AppUser addNewUser(String username,String password,String mail,String confirmPassword);
    UserRole addNewRole(String role);
    void addRoleToUser(String username,String role);
    void removeRoleFromUser(String username,String role);
    AppUser loadUserByName(String username);
}
