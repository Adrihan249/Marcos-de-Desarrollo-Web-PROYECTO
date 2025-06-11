package com.example.albergeMasc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para manejar las rutas de las páginas generales de la aplicación.
 * Incluye la redirección a la página de inicio y la visualización del nombre de usuario autenticado.
 */
@Controller
public class Controler { // Manteniendo tu nombre de clase 'Controler'

    /**
     * Redirige la URL raíz de la aplicación a la página de inicio (/index).
     * Esto asegura que, después del login, Spring Security redirija aquí
     * y luego a /index, manteniendo un flujo consistente con la configuración
     * defaultSuccessUrl de Spring Security.
     * @return Una redirección a /index.
     */
    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";
    }

    // Método auxiliar para añadir el nombre de usuario al modelo
    private void addUsernameToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            model.addAttribute("username", authentication.getName());
        } else {
            model.addAttribute("username", "Invitado");
        }
    }

    /**
     * Muestra la página de inicio (index.html).
     * Obtiene el nombre del usuario autenticado y lo añade al modelo para mostrarlo en la vista.
     * También mantiene el atributo "nombreCentro".
     * @param model El objeto Model para añadir atributos a la vista.
     * @return El nombre de la vista "index".
     */
    @GetMapping("/index")
    public String index(Model model) {
        addUsernameToModel(model); // Añade el nombre de usuario al modelo
        model.addAttribute("nombreCentro", "🐾 Patitas Felices - Adopta con Amor"); // Mantiene tu atributo existente
        return "index";
    }

    /**
     * Muestra la página 'page1.html' (Catálogo de Mascotas).
     * También pasa el nombre de usuario autenticado al modelo para el header compartido.
     * @param model El objeto Model para añadir atributos a la vista.
     * @return El nombre de la vista "page1".
     */
    @GetMapping("/page1")
    public String page1(Model model) {
        addUsernameToModel(model); // Añade el nombre de usuario al modelo
        return "page1";
    }

    /**
     * Muestra la página de adopción (form.html).
     * También pasa el nombre de usuario autenticado al modelo para el header compartido.
     * @param model El objeto Model para añadir atributos a la vista.
     * @return El nombre de la vista "form".
     */
    @GetMapping("/adopta")
    public String adopta(Model model) { // Añadimos Model model para pasar el nombre de usuario
        addUsernameToModel(model); // Añade el nombre de usuario al modelo
        return "form";
    }

    /**
     * Muestra la página del blog (blog.html).
     * También pasa el nombre de usuario autenticado al modelo para el header compartido.
     * @param model El objeto Model para añadir atributos a la vista.
     * @return El nombre de la vista "blog".
     */
    @GetMapping("/blog")
    public String blog(Model model) { // Añadimos Model model para pasar el nombre de usuario
        addUsernameToModel(model); // Añade el nombre de usuario al modelo
        return "blog";
    }
}