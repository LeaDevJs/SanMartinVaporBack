package com.sanmartinvapor.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"https://sanmartinvapor.vercel.app", "http://localhost:3000"}, allowCredentials = "true")
public class AuthController {

    @PostMapping("/login-success")
    public String loginSuccess(Authentication authentication) {
        return "✅ Login exitoso: " + authentication.getName();
    }

    @PostMapping("/login-failure")
    public String loginFailure() {
        return "❌ Usuario o contraseña incorrectos";
    }

    @GetMapping("/")
    public String home() {
        return "Backend activo 🚂";
    }
}
