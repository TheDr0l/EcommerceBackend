package com.dilan.ecommerce.service;

import com.dilan.ecommerce.model.Carrito;
import com.dilan.ecommerce.model.ItemCarrito;
import com.dilan.ecommerce.model.Producto;
import com.dilan.ecommerce.model.Usuario;
import com.dilan.ecommerce.repository.CarritoRepository;
import com.dilan.ecommerce.repository.ProductoRepository;
import com.dilan.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;

    // Obtener carrito (o crearlo si no existe)
    public Carrito obtenerCarrito(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return carritoRepository.findByUsuarioId(usuario.getId())
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    @Transactional // Importante para asegurar la integridad de datos
    public Carrito agregarItem(String emailUsuario, Long productoId, Integer cantidad) {
        Carrito carrito = obtenerCarrito(emailUsuario);
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Verificar si el producto ya está en el carrito
        Optional<ItemCarrito> itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst();

        if (itemExistente.isPresent()) {
            // Si ya existe, sumamos la cantidad
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            // Si no existe, creamos uno nuevo
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(cantidad);
            nuevoItem.setPrecioUnitario(producto.getPrecio());
            nuevoItem.setCarrito(carrito);
            carrito.getItems().add(nuevoItem);
        }

        carrito.calcularTotal();
        return carritoRepository.save(carrito);
    }

    @Transactional
    public Carrito eliminarItem(String emailUsuario, Long productoId) {
        Carrito carrito = obtenerCarrito(emailUsuario);
        
        // Eliminar el item de la lista
        carrito.getItems().removeIf(item -> item.getProducto().getId().equals(productoId));
        
        carrito.calcularTotal();
        return carritoRepository.save(carrito);
    }
}