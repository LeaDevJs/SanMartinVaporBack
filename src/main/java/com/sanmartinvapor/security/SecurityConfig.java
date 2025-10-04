package com.sanmartinvapor.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    // 🔑 Encriptador de contraseñas
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 🔑 Proveedor de autenticación con AdminUserDetailsService
    @Bean
    DaoAuthenticationProvider authProvider(AdminUserDetailsService uds, PasswordEncoder pe) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(pe);
        return p;
    }

    // 🔐 Configuración principal de seguridad
    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desactiva CSRF (para compatibilidad con React)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // permite CORS
                .authorizeHttpRequests(auth -> auth
                        // ✅ Rutas públicas
                        .requestMatchers("/health").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/personal/**", "/admin/servicios/**").permitAll()

                        // 🔒 Rutas protegidas (solo admin)
                        .requestMatchers(HttpMethod.POST, "/admin/personal/**", "/admin/servicios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/personal/**", "/admin/servicios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/personal/**", "/admin/servicios/**").hasRole("ADMIN")

                        // 🔒 cualquier otra ruta necesita autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login.permitAll()) // habilita login de formulario (para el admin)
                .logout(logout -> logout.permitAll())   // logout permitido
                .httpBasic(httpBasic -> {});            // por compatibilidad con Postman

        return http.build();
    }

    // 🌍 Configuración global de CORS (para localhost y vercel)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://sanmartinvapor.vercel.app"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // 🔑 permite cookies (JSESSIONID)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
