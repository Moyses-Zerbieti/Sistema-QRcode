package com.codigoQR.sistemQrCode.security;

import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.service.UsuarioService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomDetailsService implements UserDetailsService {

    private UsuarioService service;

    public CustomDetailsService(UsuarioService service){
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = service.ObterPorLogin(login);

        if (usuario == null){
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}