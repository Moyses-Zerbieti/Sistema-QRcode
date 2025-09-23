package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.exception.ValidacaoException;
import com.codigoQR.sistemQrCode.model.Funcionario;
import com.codigoQR.sistemQrCode.model.Veiculo;
import com.codigoQR.sistemQrCode.repository.FuncionarioRepository;
import com.codigoQR.sistemQrCode.repository.VeiculoRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastroValidacao {

    private FuncionarioRepository repository;
    private VeiculoRepository veiculoRepository;

    public CadastroValidacao(FuncionarioRepository repository, VeiculoRepository veiculoRepository){
        this.repository = repository;
        this.veiculoRepository = veiculoRepository;
    }

    public void validarFuncionario(Funcionario funcionario){
        if (repository.existsByCpf(funcionario.getCpf())) {
            throw new ValidacaoException("CPF já cadastrado.");
        }
        if (repository.existsByEmailCorporativo(funcionario.getEmailCorporativo())){
            throw new ValidacaoException("Email corporativo já cadastrado.");
        }
    }

    public void validarCadastroVeiculo(Veiculo veiculo){
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())){
            throw new ValidacaoException("Veiculo com placa " + veiculo.getPlaca() + " já cadastrado");
        }
    }
}