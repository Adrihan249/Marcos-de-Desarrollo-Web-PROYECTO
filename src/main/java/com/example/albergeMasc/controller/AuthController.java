package com.example.albergeMasc.controller;

import com.example.albergeMasc.modelo.Usuario;
import com.example.albergeMasc.servicios.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Has cerrado sesión correctamente.");
        }
        if (registered != null) {
            model.addAttribute("registered", "¡Registro exitoso! Por favor, inicia sesión.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Usuario usuario, Model model) {
        try {
            userService.registerNewUser(usuario.getUsername(), usuario.getPassword(), usuario.getEmail(), usuario.getNombre());
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}