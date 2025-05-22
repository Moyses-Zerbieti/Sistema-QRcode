package com.codigoQR.sistemQrCode.service;
import com.codigoQR.sistemQrCode.repository.FuncionariosRepository;
import org.springframework.stereotype.Component;

@Component
public class EmailFuncionario {

    private FuncionariosRepository repository;

    public EmailFuncionario(FuncionariosRepository repository){
        this.repository = repository;
    }
}
