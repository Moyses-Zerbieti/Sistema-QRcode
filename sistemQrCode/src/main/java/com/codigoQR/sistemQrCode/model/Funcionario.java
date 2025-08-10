package com.codigoQR.sistemQrCode.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "funcionarios", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "id_setor")
    private Setor setor;

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RegistroAcesso> registroAcesso;

    @ManyToOne
    @JoinColumn(name= "id_usuario")
    private Usuario idUsuario;

    @CreatedDate
    @Column(name = "data_cadastro")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<RegistroAcesso> getRegistroAcesso() {
        return registroAcesso;
    }

    public void setRegistroAcesso(List<RegistroAcesso> registroAcesso) {
        this.registroAcesso = registroAcesso;
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
}