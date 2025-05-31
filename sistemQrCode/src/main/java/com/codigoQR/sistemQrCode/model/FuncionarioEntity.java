package com.codigoQR.sistemQrCode.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table (name = "funcionarios", schema = "public")
@Getter
@Setter
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
}