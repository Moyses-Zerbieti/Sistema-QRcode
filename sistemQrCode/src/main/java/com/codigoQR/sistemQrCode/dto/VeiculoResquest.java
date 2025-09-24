package com.codigoQR.sistemQrCode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VeiculoResquest {


    @NotBlank(message = "O modelo veicular é obrigatório para realizar o cadastro")
    private String modeloCarro;

    @NotNull(message = "O ano do modelo veicular é obrigatório para realizar o cadastro")
    private Integer anoModelo;

    @NotBlank(message = "Os caracteres alfanumérico da placa são obrigatórios para realizar o cadastro")
    private String placa;

    @NotNull(message = "O ID do funcionário condutor é obrigatório para realizar o cadastro")
    private Integer funcionarioId;

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
}