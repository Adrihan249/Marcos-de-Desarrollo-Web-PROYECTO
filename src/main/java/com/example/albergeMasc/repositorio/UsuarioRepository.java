package com.example.albergeMasc.repositorio;

import com.example.albergeMasc.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 * Proporciona métodos CRUD y consultas personalizadas para Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /**
     * Busca un usuario por su nombre de usuario.
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Busca un usuario por su dirección de email.
     * @param email El email a buscar.
     * @return Un Optional que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si un nombre de usuario ya existe en la base de datos.
     * @param username El nombre de usuario a verificar.
     * @return true si el nombre de usuario existe, false en caso contrario.
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si una dirección de email ya existe en la base de datos.
     * @param email El email a verificar.
     * @return true si el email existe, false en caso contrario.
     */
    boolean existsByEmail(String email);
}