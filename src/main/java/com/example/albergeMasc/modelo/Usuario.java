package com.example.albergeMasc.modelo;

import jakarta.persistence.*;
import lombok.Data; // Importa la anotación @Data de Lombok

/**
 * Entidad que representa un usuario en la base de datos para el sistema de registro e inicio de sesión,
 * así como para almacenar otros datos del usuario relevantes para la aplicación (ej. en formularios).
 */
@Entity // Marca esta clase como una entidad JPA
@Table(name = "users") // Mapea esta entidad a una tabla llamada "users" en la base de datos
@Data // Anotación de Lombok: Genera automáticamente getters, setters, toString(), equals() y hashCode()
public class Usuario {

    @Id // Marca el campo 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática del ID (autoincremental en MySQL)
    private Long id;

    // Campo 'username' para el inicio de sesión. Debe ser único y no nulo.
    @Column(unique = true, nullable = false)
    private String username;

    // Campo 'password' para la contraseña del usuario. Debe ser no nulo.
    // ¡IMPORTANTE!: Esta contraseña debe almacenarse encriptada en la base de datos.
    @Column(nullable = false)
    private String password;

    // Campo 'email'. También debe ser único y no nulo.
    @Column(unique = true, nullable = false)
    private String email;

    // Campo 'nombre' para el nombre real del usuario, utilizado en otros formularios o perfiles.
    // Puedes ajustar si es nullable o no según tus requisitos.
    @Column(nullable = true) // Por defecto, permitimos que sea nulo si no se proporciona al registrarse
    private String nombre;

    // Con @Data de Lombok, no necesitas escribir manualmente los getters y setters.
    // Lombok se encarga de generarlos para todos los campos (id, username, password, email, nombre).

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}