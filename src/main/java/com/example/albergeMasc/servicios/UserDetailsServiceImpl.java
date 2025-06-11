package com.example.albergeMasc.servicios;

import com.example.albergeMasc.modelo.Usuario; // Importamos tu clase Usuario
import com.example.albergeMasc.repositorio.UsuarioRepository; // Importamos tu repositorio
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections; // Importación para Collections.emptyList()

/**
 * Implementación de UserDetailsService de Spring Security.
 * Se encarga de cargar los detalles del usuario desde la base de datos
 * durante el proceso de autenticación.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository; // Usamos tu repositorio de Usuario

    /**
     * Constructor para la inyección de dependencias.
     * @param usuarioRepository El repositorio de usuarios.
     */
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga los detalles de un usuario por su nombre de usuario.
     * Este método es invocado por Spring Security durante el proceso de login.
     * @param username El nombre de usuario a buscar.
     * @return Un objeto UserDetails que contiene la información del usuario autenticado.
     * @throws UsernameNotFoundException Si el usuario no se encuentra.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario en la base de datos utilizando el UsuarioRepository
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Construye y devuelve un objeto UserDetails de Spring Security.
        // El primer parámetro es el nombre de usuario, el segundo es la contraseña (ya encriptada),
        // y el tercero es una colección de roles/autoridades (vacío para este ejemplo, pero se puede extender).
        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                Collections.emptyList() // Si no tienes roles, puedes usar una lista vacía de autoridades
                // Si tuvieras roles, sería algo como:
                // user.getRoles().stream()
                //     .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                //     .collect(Collectors.toList())
        );
    }
}