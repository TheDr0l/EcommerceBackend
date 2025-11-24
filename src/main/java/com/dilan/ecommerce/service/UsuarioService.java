package com.dilan.ecommerce.service;

import com.dilan.ecommerce.dto.RegistroRequest;
import com.dilan.ecommerce.model.Role;
import com.dilan.ecommerce.model.Usuario;
import com.dilan.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(RegistroRequest request) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setDireccion(request.getDireccion());
        usuario.setTelefono(request.getTelefono());
        
        // ¡IMPORTANTE! Encriptamos la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Por defecto, todos son USER (luego podrías cambiar lógica para ADMIN)
        usuario.setRol(Role.USER);

        return usuarioRepository.save(usuario);
    }
}