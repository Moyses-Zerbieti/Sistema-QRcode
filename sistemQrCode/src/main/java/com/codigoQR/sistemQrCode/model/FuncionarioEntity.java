package com.codigoQR.sistemQrCode.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table (name = "funcionarios", schema = "public")
public class FuncionarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome_completo", length = 100, nullable = false)
    private  String nomeCompleto;

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private  String cpf;

    @Column(name = "data_nascimento", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @Column(name = "matricula", nullable = false, unique = true)
    private UUID matricula;

    @Column(name = "email_corporativo",length = 100,nullable = false,unique = true)
    private String emailCorporativo;

    @Column(name = "cargo", length = 20, nullable = false)
    private String cargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "setor", length = 30,nullable = false)
    private SetorTrabalho setor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public UUID getMatricula() {
        return matricula;
    }

    public void setMatricula(UUID matricula) {
        this.matricula = matricula;
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