package com.example.employeemanagementapp.security.service;

import com.example.employeemanagementapp.security.entities.AppUser;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsImpl implements UserDetailsService {
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByName(username);
        // Si l'utilisateur avec l'username donné n'existe pas
        if(appUser==null) throw new UsernameNotFoundException(String.format("User %s not found", username));
        // Récupère la liste des rôles sous forme de tableau de chaînes
        String[] roles= appUser.getRoles().stream().map(u ->u.getRole()).toArray(String[]::new);
        // Construit un objet UserDetails avec les informations de l'utilisateur
        UserDetails userDetails = User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles).build();

        return userDetails;
    }
}
