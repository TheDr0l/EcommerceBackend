package com.dilan.ecommerce.security;

import com.dilan.ecommerce.model.Usuario;
import com.dilan.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscamos el usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // Convertimos nuestro Usuario a un UserDetails de Spring Security
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword()) // Spring verificará esto internamente
                .roles(usuario.getRol().name())  // Asigna el rol (USER o ADMIN)
                .build();
    }
}