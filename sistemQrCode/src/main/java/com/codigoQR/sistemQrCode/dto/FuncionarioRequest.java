package com.codigoQR.sistemQrCode.dto;

import com.codigoQR.sistemQrCode.model.SetorTrabalho;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class FuncionarioRequest {

    @NotBlank(message = "O nome completo é obrigatório.")
    private String nomeCompleto;

    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotBlank(message = "O email corporativo é obrigatório.")
    @Email(message = "O email corporativo deve ser válido.")
    private String emailCorporativo;

    @NotBlank(message = "O cargo é obrigatório.")
    private String cargo;

    @NotNull(message = "O setor é obrigatório.")
    private SetorTrabalho setor;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmailCorporativo() {
        return emailCorporativo;
    }

    public void setEmailCorporativo(String emailCorporativo) {
        this.emailCorporativo = emailCorporativo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public SetorTrabalho getSetor() {
        return setor;
    }

    public void setSetor(SetorTrabalho setor) {
        this.setor = setor;
    }
}

