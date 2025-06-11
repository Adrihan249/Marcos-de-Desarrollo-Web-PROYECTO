package com.example.albergeMasc.controller; // Usamos tu paquete de controlador

import com.example.albergeMasc.modelo.Usuario; // Importamos tu clase Usuario
import com.example.albergeMasc.servicios.UserService; // Importamos tu servicio de usuario
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador para manejar las solicitudes de registro e inicio de sesión.
 */
@Controller
public class AuthController {

    private final UserService userService;

    /**
     * Constructor para la inyección de dependencias del UserService.
     * @param userService El servicio para la gestión de usuarios.
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Muestra el formulario de inicio de sesión.
     * @param error Parámetro opcional para mostrar mensajes de error de login (manejado por Spring Security).
     * @param logout Parámetro opcional para mostrar mensaje de logout exitoso.
     * @param registered Parámetro opcional para mostrar mensaje de registro exitoso.
     * @param model El objeto Model para añadir atributos a la vista.
     * @return El nombre de la vista "login".
     */
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
        return "login"; // Retorna el nombre de la plantilla Thymeleaf (login.html)
    }

    /**
     * Muestra el formulario de registro.
     * Añade un objeto Usuario vacío al modelo para el binding del formulario.
     * @param model El objeto Model para añadir atributos a la vista.
     * @return El nombre de la vista "register".
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Usuario()); // Se usa para vincular los datos del formulario al objeto Usuario
        return "register"; // Retorna el nombre de la plantilla Thymeleaf (register.html)
    }

    /**
     * Procesa el envío del formulario de registro.
     * Intenta registrar un nuevo usuario y redirige al login si es exitoso,
     * o vuelve al formulario de registro con un mensaje de error si falla.
     * @param usuario El objeto Usuario con los datos del formulario.
     * @param model El objeto Model para añadir atributos a la vista.
     * @return Una redirección a /login si el registro es exitoso, o "register" si hay errores.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Usuario usuario, Model model) {
        try {
            // CAMBIO AQUÍ: Pasamos el campo 'nombre' al servicio
            userService.registerNewUser(usuario.getUsername(), usuario.getPassword(), usuario.getEmail(), usuario.getNombre());
            // Redirige a la página de login con un parámetro para indicar que el registro fue exitoso
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage()); // Añade el mensaje de error al modelo
            return "register"; // Vuelve al formulario de registro para mostrar el error
        }
    }
}