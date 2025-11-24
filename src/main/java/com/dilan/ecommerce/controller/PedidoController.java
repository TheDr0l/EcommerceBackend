package com.dilan.ecommerce.controller;

import com.dilan.ecommerce.model.Pedido;
import com.dilan.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/checkout")
    public ResponseEntity<Pedido> checkout(Principal principal) {
        return ResponseEntity.ok(pedidoService.crearPedido(principal.getName()));
    }
}