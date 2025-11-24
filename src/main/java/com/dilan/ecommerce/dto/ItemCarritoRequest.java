package com.dilan.ecommerce.dto;
import lombok.Data;

@Data
public class ItemCarritoRequest {
    private Long productoId;
    private Integer cantidad;
}