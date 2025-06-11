package com.example.albergeMasc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager; // Importa AuthenticationManager
import org.springframework.security.authentication.ProviderManager; // Importa ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections; // Para ProviderManager

/**
 * Clase de configuración para Spring Security.
 * Define las reglas de acceso, el formulario de login y la gestión de contraseñas.
 */
@Configuration
@EnableWebSecurity // Habilita la configuración de seguridad web de Spring
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    /**
     * Inyección de dependencias para UserDetailsService.
     * @param userDetailsService El servicio que carga los detalles del usuario.
     */
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Define el PasswordEncoder a utilizar.
     * BCryptPasswordEncoder es un algoritmo de hashing fuerte y recomendado.
     * @return Una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el AuthenticationManager, que es el encargado de procesar la autenticación.
     * Se crea un DaoAuthenticationProvider y se le asigna el UserDetailsService y el PasswordEncoder.
     * @return Una instancia de AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    /**
     * Configura el filtro de seguridad HTTP para la aplicación.
     * Define qué rutas son públicas, protegidas, la página de login, etc.
     * @param http El objeto HttpSecurity para configurar la seguridad.
     * @return El SecurityFilterChain construido.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configura las reglas de autorización para las solicitudes HTTP
                .authorizeHttpRequests(authorize -> authorize
                        // Permite el acceso sin autenticación a las rutas de registro y login,
                        // así como a los recursos estáticos (CSS, JS, imágenes).
                        .requestMatchers("/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                        // Cualquier otra solicitud requiere autenticación.
                        .anyRequest().authenticated()
                )
                // Configura el formulario de inicio de sesión
                .formLogin(form -> form
                        .loginPage("/login") // Especifica la URL de la página de login personalizada
                        .defaultSuccessUrl("/index", true) // Redirige a /index después de un login exitoso
                        .permitAll() // Permite a todos acceder a la página de login
                )
                // Configura el proceso de cierre de sesión
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para la acción de cierre de sesión
                        .logoutSuccessUrl("/login?logout") // Redirige a /login con un parámetro después del logout
                        .permitAll() // Permite a todos acceder a la URL de logout
                );
        // Si necesitas deshabilitar la protección CSRF (por ejemplo, para APIs REST sin sesiones), lo harías así:
        // .csrf(csrf -> csrf.disable());

        return http.build(); // Construye y devuelve el filtro de seguridad
    }
}