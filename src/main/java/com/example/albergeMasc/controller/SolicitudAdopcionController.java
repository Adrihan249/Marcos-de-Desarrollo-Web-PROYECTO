package com.example.albergeMasc.controller;

import com.example.albergeMasc.modelo.SolicitudesAdopcion;
import com.example.albergeMasc.repositorio.SolicitudAdopcionRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/form")
public class SolicitudAdopcionController {

    @Autowired
    private SolicitudAdopcionRepositorio solicitudAdopcionRepositorio;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("solicitud", new SolicitudesAdopcion());
        return "form";
    }

    @PostMapping
    public String procesarFormulario(
            @Valid @ModelAttribute("solicitud") SolicitudesAdopcion solicitud,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "form";  // Devuelve el formulario con errores
        }

        solicitudAdopcionRepositorio.save(solicitud);
        model.addAttribute("mensajeExito", "¡Formulario enviado exitosamente!");

        // Esta es la clave para mostrar el modal:
        model.addAttribute("mostrarModal", true);

        // Vaciar el formulario después de enviar (opcional)
        model.addAttribute("solicitud", new SolicitudesAdopcion());

        return "form";  // Vuelve a mostrar el formulario
    }
}
