package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.model.Funcionario;
import com.codigoQR.sistemQrCode.repository.FuncionarioRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarDadosValidacao {

    private FuncionarioRepository repository;

    public AtualizarDadosValidacao(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public void validarExisteFuncionario(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Funcionário com ID " + id + " não encontrado.");
        }
    }

    public void validarDuplicidade(Funcionario funcionario){
        Funcionario existentePorCpf = repository.findByCpf(funcionario.getCpf());
        Funcionario existentePorEmail = repository.findByEmailCorporativo(funcionario.getEmailCorporativo());

        boolean cpfDuplicado = existentePorCpf != null && !existentePorCpf.getId().equals(funcionario.getId());
        boolean emailDuplicado = existentePorEmail != null && !existentePorEmail.getId().equals(funcionario.getId());


        if (cpfDuplicado || emailDuplicado){
            throw new RuntimeException("Já existe um funcionário com esse CPF ou Email corporativo");
        }
    }

    public Funcionario buscarOuFalhar(Integer id){
        return repository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Funcionário com id "  + id + " não encontrado."));

    }
}