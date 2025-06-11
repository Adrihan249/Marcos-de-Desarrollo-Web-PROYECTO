package com.example.albergeMasc.servicios;

import com.example.albergeMasc.modelo.Usuario;
import com.example.albergeMasc.repositorio.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para la gestión de usuarios, incluyendo el registro y la búsqueda.
 */
@Service
public class UserService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Inyectado para encriptar contraseñas

    /**
     * Constructor para la inyección de dependencias.
     * @param usuarioRepository El repositorio de usuarios.
     * @param passwordEncoder El codificador de contraseñas de Spring Security.
     */
    public UserService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * La contraseña se encripta antes de guardarse.
     * @param username Nombre de usuario del nuevo usuario.
     * @param password Contraseña del nuevo usuario (será encriptada).
     * @param email Email del nuevo usuario.
     * @param nombre Nombre del nuevo usuario. // CAMBIO AQUÍ: ¡Añadimos 'nombre' como parámetro!
     * @return El usuario registrado.
     * @throws RuntimeException Si el nombre de usuario o el email ya existen.
     */
    public Usuario registerNewUser(String username, String password, String email, String nombre) { // CAMBIO AQUÍ: Agregamos 'String nombre'
        // Verifica si el nombre de usuario ya existe
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("El nombre de usuario ya existe.");
        }
        // Verifica si el email ya existe
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password)); // ¡IMPORTANTE! Encripta la contraseña
        usuario.setEmail(email);
        usuario.setNombre(nombre); // Ahora 'nombre' se asigna correctamente

        return usuarioRepository.save(usuario); // Guarda el nuevo usuario en la base de datos
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra.
     */
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}