package com.example.LearnAuthentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerLoginController {

    @GetMapping("/swagger-login")
    public String swaggerLoginPage() {
        return "redirect:/swagger-login.html";
    }

    @GetMapping({"/swagger-ui/index.html", "/swagger-ui.html"})
    public String swaggerUiPage() {
        return "redirect:/swagger-auth-ui.html";
    }
}
