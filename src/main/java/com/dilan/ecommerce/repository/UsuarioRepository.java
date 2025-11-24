package com.dilan.ecommerce.repository;

import com.dilan.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Busca por email automáticamente
    Optional<Usuario> findByEmail(String email);
    
    // Verifica si existe un email
    boolean existsByEmail(String email);
}