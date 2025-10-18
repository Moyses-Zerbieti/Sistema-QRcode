package com.codigoQR.sistemQrCode.dto;

import java.util.List;
import java.util.UUID;

public class UsuarioRequest {
    private String login;
    private String senha;
    private List <String> roles;

    public UsuarioRequest(String login, String senha, List<String> roles){
        this.login = login;
        this.senha = senha;
        this.roles = roles;
    }

    public UsuarioRequest(){

    }

    public String getLogin() {
        return login;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}