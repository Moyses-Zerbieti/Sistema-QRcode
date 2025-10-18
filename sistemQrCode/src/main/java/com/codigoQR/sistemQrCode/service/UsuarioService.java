package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.dto.UsuarioRequest;
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

    public void salvar(UsuarioRequest usuarioDTO){
        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(usuarioDTO.getLogin());
        novoUsuario.setSenha(encoder.encode(usuarioDTO.getSenha()));
        novoUsuario.setRoles(usuarioDTO.getRoles());

        Usuario salvo = usuarioRepository.save(novoUsuario);
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