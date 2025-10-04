package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
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

    public void deletarUsuarioPorLogin(String login){
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null){
            throw new ResourceNotFoundException("Usuario com login " + login + " não encontrado");
        }
            usuarioRepository.delete(usuario);
    }
}