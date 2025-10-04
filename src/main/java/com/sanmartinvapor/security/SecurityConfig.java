package com.sanmartinvapor.security;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Encriptador de contraseñas
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Proveedor de autenticación que usa nuestro AdminUserDetailsService
    @Bean
    DaoAuthenticationProvider authProvider(AdminUserDetailsService uds, PasswordEncoder pe) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(pe);
        return p;
    }

    // Configuración de seguridad
    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // 👈 activa CorsConfig
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/personal/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/servicios/**").permitAll()
                        .anyRequest().hasRole("ADMIN")
                )
                .formLogin(login -> login.permitAll())
                .httpBasic(basic -> {})
                .logout(logout -> logout.permitAll());
        return http.build();
    }



}
