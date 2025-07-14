package com.example.albergeMasc.modelo;

import jakarta.persistence.*;
import lombok.Data; // Importa la anotación @Data de Lombok

@Entity
@Table(name = "users")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)
    private String nombre;

    // Con @Data de Lombok, no se necesita escribir manualmente los getters y setters.
}