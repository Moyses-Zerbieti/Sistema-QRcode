package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.Exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.Exception.ValidacaoException;
import com.codigoQR.sistemQrCode.dto.FuncionarioRequest;
import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.repository.FuncionariosRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceCadastro {

    private FuncionariosRepository repository;
    private CadastroValidacao validacao;
    private EmailFuncionario emailFuncionario;
    private AtualizarDadosValidacao atualizarDadosValidacao;
    private QrCodeGenerator qrCodeGenerator;

    public ServiceCadastro(FuncionariosRepository repository,
                           CadastroValidacao validacao,
                           EmailFuncionario emailFuncionario,
                           AtualizarDadosValidacao atualizarDadosValidacao,
                           QrCodeGenerator qrCodeGenerator) {
        this.repository = repository;
        this.validacao = validacao;
        this.emailFuncionario = emailFuncionario;
        this.atualizarDadosValidacao = atualizarDadosValidacao;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    public FuncionarioEntity salvar(FuncionarioRequest dto) {
        FuncionarioEntity novoFuncionarioEntity = new FuncionarioEntity();

        novoFuncionarioEntity.setNomeCompleto(dto.getNomeCompleto());
        novoFuncionarioEntity.setCpf(dto.getCpf());
        novoFuncionarioEntity.setDataNascimento(dto.getDataNascimento());
        novoFuncionarioEntity.setEmailCorporativo(dto.getEmailCorporativo());
        novoFuncionarioEntity.setCargo(dto.getCargo());
        novoFuncionarioEntity.setSetor(dto.getSetor());
        novoFuncionarioEntity.setMatricula(UUID.randomUUID());

        validacao.validarFuncionario(novoFuncionarioEntity);

        FuncionarioEntity salvo = repository.save(novoFuncionarioEntity);

        try {
            String textoQrCode = "Funcionario ID: " + salvo.getId() + ", Nome: " + salvo.getNomeCompleto();
            byte[] imagemQrCode = qrCodeGenerator.gerarQrCode(textoQrCode);
            emailFuncionario.enviarQrCodePorEmail(salvo.getEmailCorporativo(), imagemQrCode);
        } catch (Exception e) {
            System.err.println("Erro ao gerar/enviar QR CODE: " + e.getMessage());
        }

        return salvo;
    }

    public FuncionarioEntity consultaId(Integer id) {
        return atualizarDadosValidacao.buscarOuFalhar(id);
    }

    public FuncionarioEntity atualizarInformacoes(@NotNull FuncionarioEntity funcionarioEntity){
        atualizarDadosValidacao.validarExisteFuncionario(funcionarioEntity.getId());

        FuncionarioEntity funcionarioAtual = repository.findById(funcionarioEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário com id " + funcionarioEntity.getId() + " não encontrado"));

        if (!funcionarioAtual.getCpf().equals(funcionarioEntity.getCpf())) {
            throw new ValidacaoException("Não é permitido alterar o CPF.");
        }

        if (!funcionarioAtual.getEmailCorporativo().equals(funcionarioEntity.getEmailCorporativo())) {
            throw new ValidacaoException("Não é permitido alterar o Email corporativo.");
        }

        funcionarioAtual.setNomeCompleto(funcionarioEntity.getNomeCompleto());
        funcionarioAtual.setCargo(funcionarioEntity.getCargo());
        funcionarioAtual.setSetor(funcionarioEntity.getSetor());
        funcionarioAtual.setDataNascimento(funcionarioEntity.getDataNascimento());

        return repository.save(funcionarioAtual);
    }

    public void deletarFuncionario(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Funcionário com id " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }
}

