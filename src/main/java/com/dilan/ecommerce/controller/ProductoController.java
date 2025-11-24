package com.dilan.ecommerce.controller;

import com.dilan.ecommerce.model.Producto;
import com.dilan.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:5173") // Permitir peticiones desde cualquier lado (para desarrollo)
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET: Listar todos
    @GetMapping
    public List<Producto> listar() {
        return productoService.listarProductos();
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        // Si existe devolvemos 200 OK, si no devolvemos 404 Not Found
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear nuevo producto
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    // PUT: Editar producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> editar(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> productoExistente = productoService.obtenerProductoPorId(id);
        
        if (productoExistente.isPresent()) {
            producto.setId(id); // Aseguramos que el ID sea el de la URL
            return ResponseEntity.ok(productoService.guardarProducto(producto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Borrar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        if (producto.isPresent()) {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}