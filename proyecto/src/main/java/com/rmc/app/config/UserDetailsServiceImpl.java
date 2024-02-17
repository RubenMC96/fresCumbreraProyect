package com.rmc.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.rmc.app.Repositories.UsuarioRepository;
import com.rmc.app.domain.Usuario;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username);
        if (usuario == null)
            throw (new UsernameNotFoundException("Usuario no encontrado!"));
        return User // org.springframework.security.core.userdetails.User
                .withUsername(username)
                .roles(usuario.getRol().toString())
                .password(usuario.getPassword())
                .build();
    }
}
