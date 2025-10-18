package com.codigoQR.sistemQrCode.dto;

public class SetorRequest {

    private String nomeSetor;

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public SetorRequest(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public SetorRequest(){

    }
}