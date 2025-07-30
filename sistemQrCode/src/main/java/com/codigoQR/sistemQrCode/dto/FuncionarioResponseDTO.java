package com.codigoQR.sistemQrCode.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class FuncionarioResponseDTO {
    private Integer id;
    private String nomeCompleto;
    private String cpf;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    private UUID matricula;
    private String emailCorporativo;
    private String cargo;
    private String setor;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;


    public FuncionarioResponseDTO(
            Integer id,
            String nomeCompleto,
            String cpf,
            LocalDate dataNascimento,
            UUID matricula,
            String emailCorporativo,
            String cargo,
            String setor,
            LocalDateTime dataCadastro,
            LocalDateTime dataAtualizacao) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.matricula = matricula;
        this.emailCorporativo = emailCorporativo;
        this.cargo = cargo;
        this.setor = setor;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }
    public String getCpf() {
        return cpf;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public UUID getMatricula() {
        return matricula;
    }
    public String getEmailCorporativo() {
        return emailCorporativo;
    }
    public String getCargo() {
        return cargo;
    }
    public String getSetor() {
        return setor;
    }
    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}