package com.codigoQR.sistemQrCode.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table (name = "veiculo")
@EntityListeners(AuditingEntityListener.class)
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "modelo_carro", length = 30, nullable = false)
    private String modeloCarro;

    @Column(name = "ano_modelo", length = 5, nullable = false)
    private Integer anoModelo;

    @Column(name = "placa", length = 10, nullable = false)
    private String placa;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionarioId;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastroVeiculo;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacaoVeiculo;

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

    public Funcionario getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Funcionario funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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