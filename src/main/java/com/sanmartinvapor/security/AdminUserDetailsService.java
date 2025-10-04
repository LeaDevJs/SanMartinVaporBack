package com.sanmartinvapor.security;

import com.sanmartinvapor.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;               // ✅ ESTA es la correcta
import org.springframework.security.core.userdetails.UserDetails;     // interfaz
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository repo;

    public AdminUserDetailsService(AdminRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var admin = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin no encontrado"));

        return User.withUsername(admin.getUsername())
                .password(admin.getPassword()) // la contraseña encriptada de la BD
                .roles("ADMIN")                // rol fijo
                .build();
    }
}
