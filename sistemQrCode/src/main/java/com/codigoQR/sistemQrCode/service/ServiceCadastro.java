package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.repository.FuncionariosRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceCadastro {

    private FuncionariosRepository repository;
    private CadastroValidacao validacao;
    private  EmailFuncionario emailFuncionario;

    public ServiceCadastro(FuncionariosRepository repository, CadastroValidacao validacao, EmailFuncionario emailFuncionario) {
        this.repository = repository;
        this.validacao = validacao;
        this.emailFuncionario = emailFuncionario;
    }

    public FuncionarioEntity salvar (FuncionarioEntity novoFuncionarioEntity){
        return repository.save(novoFuncionarioEntity);
    }

    public FuncionarioEntity atualizarInformacoes(FuncionarioEntity funcionarioEntity){
        return repository.save(funcionarioEntity);

    }

    public FuncionarioEntity consultaId(Integer id){
        return repository.findById(id).orElse(null);
    }

}
