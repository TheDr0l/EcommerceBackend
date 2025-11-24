package com.dilan.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementable
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false) // El email no puede repetirse
    private String email;

    private String direccion;

    private String telefono;
    
    private String password; // En pasos futuros la encriptaremos

    @Enumerated(EnumType.STRING) // Guarda el texto "ADMIN" o "USER" en la BD
    private Role rol;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    // Se ejecuta automáticamente antes de guardar el registro por primera vez
    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}