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
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health", "/login").permitAll() // ✅ permite login desde React
                        .requestMatchers(HttpMethod.GET, "/admin/personal/**", "/admin/servicios/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/personal/**", "/admin/servicios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/personal/**", "/admin/servicios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/personal/**", "/admin/servicios/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginProcessingUrl("/login")
                        .successForwardUrl("/login-success")  // ✅ redirige acá si el login fue correcto
                        .failureForwardUrl("/login-failure")  // ❌ si las credenciales fallan
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                )
                .httpBasic(httpBasic -> {});
        return http.build();
    }

    // 🌍 Configuración global de CORS (para frontend en localhost y Vercel)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://sanmartinvapor.vercel.app"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // ✅ necesario para mantener sesión (cookie JSESSIONID)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
