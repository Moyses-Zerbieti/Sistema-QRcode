package com.codigoQR.sistemQrCode.dto;


public class AtualizacaoVeiculoDTO {

    private String placa;

    public AtualizacaoVeiculoDTO(String placa){
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}