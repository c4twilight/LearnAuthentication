package com.example.LearnAuthentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerLoginController {

    @GetMapping("/swagger-login")
    public String swaggerLoginPage() {
        return "redirect:/swagger-login.html";
    }
}
