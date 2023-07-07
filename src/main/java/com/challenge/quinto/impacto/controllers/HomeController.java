package com.challenge.quinto.impacto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String goIndex(Model model, Principal principal){

        boolean isLoggedIn = false;

        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            isLoggedIn = authentication.isAuthenticated();
        }

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("title", "Inicio - U.Q.I.");
        return "web/index";
    }

    @GetMapping("/admin")
    public String goAdmin(Model model, Principal principal){

        boolean isLoggedIn = false;

        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            isLoggedIn = authentication.isAuthenticated();
        }

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("title", "Panel de administracion");

        return "admin/admin";
    }

    @GetMapping("/courses")
    public String goCourse(Model model, Principal principal){

        boolean isLoggedIn = false;

        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            isLoggedIn = authentication.isAuthenticated();
        }

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("title", "Cursos - U.Q.I.");

        return "web/courses";
    }

    @GetMapping("/login")
    public String goLogin(Model model, Principal principal){

        boolean isLoggedIn = false;

        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            isLoggedIn = authentication.isAuthenticated();
        }

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("title", "Acceso Estudiantes - U.Q.I.");

        return "web/login-register";
    }

    @GetMapping("/login-teacher")
    public String goLoginTeacher(Model model, Principal principal){

        boolean isLoggedIn = false;

        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            isLoggedIn = authentication.isAuthenticated();
        }

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("title", "Acceso Profesor - U.Q.I.");

        return "web/login-register-teacher";
    }

    @GetMapping("/profile")
    public String goProfile(Model model, Principal principal){

        boolean isLoggedIn = false;

        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            isLoggedIn = authentication.isAuthenticated();
        }

        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("title", "Perfil - U.Q.I.");

        return "web/profile";
    }

}
