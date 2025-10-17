package com.codigoQR.sistemQrCode.dto;

public class CargoRequest {

    private String nomeCargo;

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public CargoRequest(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public CargoRequest(){

    }
}