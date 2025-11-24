package com.dilan.ecommerce.repository;
import com.dilan.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioEmail(String email); // Para que el usuario vea su historial
}