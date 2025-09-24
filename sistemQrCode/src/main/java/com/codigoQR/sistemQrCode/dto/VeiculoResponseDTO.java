package com.codigoQR.sistemQrCode.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class VeiculoResponseDTO {

    private Integer id;
    private String modeloCarro;
    private Integer anoModelo;
    private String placa;
    private FuncionarioResponseVeiculoDTO funcionario;


    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataCadastroVeiculo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataAtualizacaoVeiculo;

    public VeiculoResponseDTO(
            Integer id,
            String modeloCarro,
            Integer anoModelo,
            String placa,
            FuncionarioResponseVeiculoDTO funcionario,
            LocalDateTime dataCadastroVeiculo,
            LocalDateTime dataAtualizacaoVeiculo) {
        this.id = id;
        this.modeloCarro = modeloCarro;
        this.anoModelo = anoModelo;
        this.placa = placa;
        this.funcionario = funcionario;
        this.dataCadastroVeiculo = dataCadastroVeiculo;
        this.dataAtualizacaoVeiculo = dataAtualizacaoVeiculo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public FuncionarioResponseVeiculoDTO getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioResponseVeiculoDTO funcionario) {
        this.funcionario = funcionario;
    }

    public LocalDateTime getDataCadastroVeiculo() {
        return dataCadastroVeiculo;
    }

    public void setDataCadastroVeiculo(LocalDateTime dataCadastroVeiculo) {
        this.dataCadastroVeiculo = dataCadastroVeiculo;
    }

    public LocalDateTime getDataAtualizacaoVeiculo() {
        return dataAtualizacaoVeiculo;
    }

    public void setDataAtualizacaoVeiculo(LocalDateTime dataAtualizacaoVeiculo) {
        this.dataAtualizacaoVeiculo = dataAtualizacaoVeiculo;
    }

}