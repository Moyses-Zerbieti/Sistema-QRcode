package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder encoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder encoder){
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public void salvar(Usuario usuario){
        var senha  = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        usuarioRepository.save(usuario);
    }

    public Usuario ObterPorLogin(String login){
        return usuarioRepository.findByLogin(login);
    }
}