package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.Exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.repository.FuncionariosRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarDadosValidacao {

    private FuncionariosRepository repository;

    public AtualizarDadosValidacao(FuncionariosRepository repository) {
        this.repository = repository;
    }

    public void validarExisteFuncionario(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Funcionário com ID " + id + " não encontrado.");
        }
    }

    public void validarDuplicidade(FuncionarioEntity funcionarioEntity){
        FuncionarioEntity existentePorCpf = repository.findByCpf(funcionarioEntity.getCpf());
        FuncionarioEntity existentePorEmail = repository.findByEmailCorporativo(funcionarioEntity.getEmailCorporativo());

        boolean cpfDuplicado = existentePorCpf != null && !existentePorCpf.getId().equals(funcionarioEntity.getId());
        boolean emailDuplicado = existentePorEmail != null && !existentePorEmail.getId().equals(funcionarioEntity.getId());


        if (cpfDuplicado || emailDuplicado){
            throw new RuntimeException("Já existe um funcionário com esse CPF ou Email corporativo");
        }
    }

    public FuncionarioEntity buscarOuFalhar(Integer id){
        return repository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Funcionário com id"  + id + " não encontrado."));

    }
}
