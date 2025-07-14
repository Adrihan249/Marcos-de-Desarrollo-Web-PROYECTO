    package com.example.albergeMasc.controller;

    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;

    @Controller
    public class Controler {

        @GetMapping("/")
        public String redirectToIndex() {
            return "redirect:/index";
        }

        private void addUsernameToModel(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() &&
                    !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
                model.addAttribute("username", authentication.getName());
            } else {
                model.addAttribute("username", "Invitado");
            }
        }

        @GetMapping("/index")
        public String index(Model model) {
            addUsernameToModel(model);
            model.addAttribute("nombreCentro", "🐾 Patitas Felices - Adopta con Amor");
            model.addAttribute("currentPage", "index");
            return "index";
        }

        @GetMapping("/page1")
        public String page1(Model model) {
            addUsernameToModel(model);
            model.addAttribute("currentPage", "page1");
            return "page1";
        }


        @GetMapping("/blog")
        public String blog(Model model) {
            addUsernameToModel(model);
            model.addAttribute("currentPage", "blog");
            return "blog";
        }
    }