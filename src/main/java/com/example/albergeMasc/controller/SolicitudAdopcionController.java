package com.example.albergeMasc.controller;

import com.example.albergeMasc.modelo.SolicitudesAdopcion;
import com.example.albergeMasc.repositorio.SolicitudAdopcionRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication; // Necesario
import org.springframework.security.core.context.SecurityContextHolder; // Necesario

@Controller
@RequestMapping("/form")
public class SolicitudAdopcionController {

    @Autowired
    private SolicitudAdopcionRepositorio solicitudAdopcionRepositorio;

    private void addUsernameToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            model.addAttribute("username", authentication.getName());
        } else {
            model.addAttribute("username", "Invitado");
        }
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        addUsernameToModel(model);
        model.addAttribute("solicitud", new SolicitudesAdopcion());
        model.addAttribute("currentPage", "form");
        return "form";
    }

    @PostMapping
    public String procesarFormulario(
            @Valid @ModelAttribute("solicitud") SolicitudesAdopcion solicitud,
            BindingResult result,
            Model model) {

        addUsernameToModel(model);
        model.addAttribute("currentPage", "form");

        if (result.hasErrors()) {
            return "form";
        }

        solicitudAdopcionRepositorio.save(solicitud);
        model.addAttribute("mensajeExito", "¡Formulario enviado exitosamente!");
        model.addAttribute("mostrarModal", true);
        model.addAttribute("solicitud", new SolicitudesAdopcion());

        return "form";
    }
}
