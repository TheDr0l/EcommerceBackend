package com.dilan.ecommerce.repository;

import com.dilan.ecommerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí podrás agregar búsquedas personalizadas luego, ej:
    // List<Producto> findByNombreContaining(String nombre);
}