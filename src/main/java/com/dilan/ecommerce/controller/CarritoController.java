package com.dilan.ecommerce.controller;

import com.dilan.ecommerce.dto.ItemCarritoRequest;
import com.dilan.ecommerce.model.Carrito;
import com.dilan.ecommerce.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Obtener mi carrito
    @GetMapping
    public ResponseEntity<Carrito> miCarrito(Principal principal) {
        // principal.getName() nos da el email del usuario logueado (gracias al JWT)
        return ResponseEntity.ok(carritoService.obtenerCarrito(principal.getName()));
    }

    // Agregar producto al carrito
    @PostMapping
    public ResponseEntity<Carrito> agregarItem(@RequestBody ItemCarritoRequest request, Principal principal) {
        Carrito carritoActualizado = carritoService.agregarItem(
                principal.getName(),
                request.getProductoId(),
                request.getCantidad()
        );
        return ResponseEntity.ok(carritoActualizado);
    }

    // Eliminar producto del carrito
    @DeleteMapping("/{productoId}")
    public ResponseEntity<Carrito> eliminarItem(@PathVariable Long productoId, Principal principal) {
        Carrito carritoActualizado = carritoService.eliminarItem(principal.getName(), productoId);
        return ResponseEntity.ok(carritoActualizado);
    }
}