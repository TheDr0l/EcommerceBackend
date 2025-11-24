package com.dilan.ecommerce.service;

import com.dilan.ecommerce.model.*;
import com.dilan.ecommerce.repository.CarritoRepository;
import com.dilan.ecommerce.repository.PedidoRepository;
import com.dilan.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CarritoRepository carritoRepository;
    @Autowired private CarritoService carritoService; // Reutilizamos lógica

    @Transactional
    public Pedido crearPedido(String emailUsuario) {
        Carrito carrito = carritoService.obtenerCarrito(emailUsuario);
        
        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(carrito.getUsuario());
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setTotal(carrito.getTotal());

        // Convertir ItemsCarrito a DetallesPedido
        List<DetallePedido> detalles = carrito.getItems().stream().map(item -> {
            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setPedido(pedido);
            return detalle;
        }).toList();

        pedido.setDetalles(detalles);

        // Guardar pedido
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        //IMPORTANTE: Vaciar el carrito después de comprar
        carrito.getItems().clear();
        carrito.setTotal(java.math.BigDecimal.ZERO);
        carritoRepository.save(carrito);

        return pedidoGuardado;
    }
}