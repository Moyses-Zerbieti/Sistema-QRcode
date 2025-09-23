package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.model.Funcionario;
import com.codigoQR.sistemQrCode.model.Veiculo;
import com.codigoQR.sistemQrCode.repository.FuncionarioRepository;
import com.codigoQR.sistemQrCode.repository.VeiculoRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarDadosValidacao {

    private FuncionarioRepository repository;

    private VeiculoRepository veiculoRepository;


    public AtualizarDadosValidacao(FuncionarioRepository repository,VeiculoRepository veiculoRepository){
        this.repository = repository;
        this.veiculoRepository = veiculoRepository;
    }

    public void validarExisteFuncionario(Integer id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Funcionário com ID " + id + " não encontrado.");
        }
    }

    public void validarExisteVeiculo(Integer id){
        if(!veiculoRepository.existsById(id)){
            throw new ResourceNotFoundException("Veículo com ID " + id + " não encontrado");
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

    public void validarPlacasDuplas(Veiculo veiculo){
        Veiculo existePlaca = veiculoRepository.findByPlaca(veiculo.getPlaca());

        boolean placaDuplicada  = existePlaca != null && !existePlaca.getId().equals(veiculo.getId());

        if (placaDuplicada){
            throw new RuntimeException("Já existe um veiculo com essa placa cadastrada");
        }

    }

    public Funcionario buscarOuFalhar(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário com id "  + id + " não encontrado."));

    }

    public Veiculo buscarOuFalharVeiculo(Integer id){
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo com id " + id + "não encontrado."));
    }
}