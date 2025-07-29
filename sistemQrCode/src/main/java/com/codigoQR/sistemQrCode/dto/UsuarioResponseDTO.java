package com.codigoQR.sistemQrCode.dto;

import java.util.List;
import java.util.UUID;

public class UsuarioResponseDTO {
    private UUID id;
    private String login;
    private List <String> roles;

    public UsuarioResponseDTO (UUID id, String login, List<String> roles){
        this.id = id;
        this.login = login;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<String> getRoles() {
        return roles;
    }
}