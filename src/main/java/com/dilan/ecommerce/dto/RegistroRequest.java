package com.dilan.ecommerce.dto;
import lombok.Data;

@Data
public class RegistroRequest {
    private String nombre;
    private String email;
    private String password;
    private String direccion;
    private String telefono;
}