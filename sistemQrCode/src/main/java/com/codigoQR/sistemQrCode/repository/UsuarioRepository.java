package com.codigoQR.sistemQrCode.repository;

import com.codigoQR.sistemQrCode.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository <Usuario,UUID>{
    Usuario findByLogin(String login);

    boolean existsByLogin(String login);
}