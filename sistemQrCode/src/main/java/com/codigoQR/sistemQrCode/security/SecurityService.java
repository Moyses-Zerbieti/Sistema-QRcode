package com.codigoQR.sistemQrCode.security;

import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    private UsuarioService usuarioService;

    public SecurityService (UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        return usuarioService.ObterPorLogin(login);
    }
}