package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.exception.ValidacaoException;
import com.codigoQR.sistemQrCode.model.Funcionario;
import com.codigoQR.sistemQrCode.repository.FuncionarioRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastroValidacao {

    private FuncionarioRepository repository;

    public CadastroValidacao(FuncionarioRepository repository){
        this.repository = repository;
    }
    public void validarFuncionario(Funcionario funcionario){
        if (repository.existsByCpf(funcionario.getCpf())) {
            throw new ValidacaoException("CPF já cadastrado.");
        }
        if (repository.existsByEmailCorporativo(funcionario.getEmailCorporativo())){
            throw new ValidacaoException("Email corporativo já cadastrado.");
        }
    }
}