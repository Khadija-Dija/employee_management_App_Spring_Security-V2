package com.example.employeemanagementapp.security.service;

import com.example.employeemanagementapp.security.entities.AppUser;
import com.example.employeemanagementapp.security.entities.UserRole;
import com.example.employeemanagementapp.security.repository.UserAppRepository;
import com.example.employeemanagementapp.security.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor @NoArgsConstructor
@Data
public class AccountServiceImpl implements AccountService {
    @Autowired
   private UserAppRepository userAppRepository;
    @Autowired
   private UserRoleRepository userRoleRepository;
    @Autowired
   private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser = userAppRepository.findByUsername(username);
        if (appUser !=null) throw new RuntimeException("This user already exist");
        if (!password.equals(confirmPassword)) throw  new RuntimeException("Password not match ");
        AppUser user = AppUser.builder()
                .userId(UUID.randomUUID())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        AppUser saveUser = userAppRepository.save(user);
        return saveUser;
    }

    @Override
    public UserRole addNewRole(String role) {

        //verifier si le role existe deja
        UserRole userRole = userRoleRepository.findById(role).orElse(null);
        if (userRole != null)
            throw new RuntimeException("This role already exist");
        userRole = UserRole.builder()
                .role(role)
                .build();
        return  userRoleRepository.save(userRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = userAppRepository.findByUsername(username);
        UserRole userRole = userRoleRepository.findById(role).get();
        appUser.getRoles().add(userRole);
        // La sauvegarde de l'utilisateur n'est pas nécessaire ici si la méthode est transactionnelle
        // Si la méthode est annotée avec @Transactional, les modifications seront automatiquement persistées à la fin de la transaction.
        //userAppRepository.save(appUser);

    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = userAppRepository.findByUsername(username);
        UserRole userRole = userRoleRepository.findById(role).get();
        appUser.getRoles().remove(userRole);
        // La sauvegarde de l'utilisateur n'est pas nécessaire ici si la méthode est transactionnelle
        // Si la méthode est annotée avec @Transactional, les modifications seront automatiquement persistées à la fin de la transaction.
        //userAppRepository.save(appUser);
    }

    @Override
    public AppUser loadUserByName(String username) {
        return userAppRepository.findByUsername(username);
    }
}
