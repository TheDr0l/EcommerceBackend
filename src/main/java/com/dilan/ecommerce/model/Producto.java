package com.dilan.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion; 

    private String urlImagen; // URL de la imagen (luego veremos Cloudinary)

    private BigDecimal precio;

    private Integer stock;

    // Opcional: Relación con una categoría si decides implementarla después
    // private String categoria; 
}