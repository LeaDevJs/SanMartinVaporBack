package com.sanmartinvapor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/admin/hello")
    public String helloAdmin() {
        return "Hola Admin, logueo exitoso 🚂";
    }
}
