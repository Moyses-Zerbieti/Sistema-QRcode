package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.exception.ValidacaoException;
import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.repository.FuncionariosRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastroValidacao {

    private FuncionariosRepository repository;

    public CadastroValidacao(FuncionariosRepository repository){
        this.repository = repository;
    }
    public void validarFuncionario(FuncionarioEntity funcionario){
        if (repository.existsByCpf(funcionario.getCpf())) {
            throw new ValidacaoException("CPF já cadastrado.");
        }
        if (repository.existsByEmailCorporativo(funcionario.getEmailCorporativo())){
            throw new ValidacaoException("Email corporativo já cadastrado.");
        }
    }
}
