package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.repository.FuncionariosRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastroValidacao {

    private FuncionariosRepository repository;

    public CadastroValidacao(FuncionariosRepository repository){
        this.repository = repository;
    }
}
