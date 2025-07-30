package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.dto.AtualizacaoDTO;
import com.codigoQR.sistemQrCode.dto.FuncionarioResponseDTO;
import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.dto.FuncionarioRequest;
import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.repository.FuncionariosRepository;
import com.codigoQR.sistemQrCode.security.SecurityService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceFuncionarios {

    private FuncionariosRepository repository;
    private CadastroValidacao validacao;
    private EmailFuncionario emailFuncionario;
    private AtualizarDadosValidacao atualizarDadosValidacao;
    private QrCodeGenerator qrCodeGenerator;
    private SecurityService securityService;

    public ServiceFuncionarios(FuncionariosRepository repository,
                               CadastroValidacao validacao,
                               EmailFuncionario emailFuncionario,
                               AtualizarDadosValidacao atualizarDadosValidacao,
                               QrCodeGenerator qrCodeGenerator,
                               SecurityService securityService) {
        this.repository = repository;
        this.validacao = validacao;
        this.emailFuncionario = emailFuncionario;
        this.atualizarDadosValidacao = atualizarDadosValidacao;
        this.qrCodeGenerator = qrCodeGenerator;
        this.securityService = securityService;
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
        Usuario usuario = securityService.obterUsuarioLogado();
        novoFuncionarioEntity.setIdUsuario(usuario);
        FuncionarioEntity salvo = repository.save(novoFuncionarioEntity);

        try {
            String textoQrCode = """
                    Nome: %s
                    Cargo: %s
                    Setor: %s
                    """.formatted(
                    salvo.getNomeCompleto(),
                    salvo.getCargo(),
                    salvo.getSetor()
                    );
            byte[] imagemQrCode = qrCodeGenerator.gerarQrCode(textoQrCode);
            emailFuncionario.enviarQrCodePorEmail(salvo.getEmailCorporativo(), imagemQrCode);
        } catch (Exception e) {
            System.err.println("Erro ao gerar/enviar QR CODE: " + e.getMessage());
        }
        return salvo;
    }

    public FuncionarioResponseDTO consultaId(Integer id) {
        FuncionarioEntity entity = atualizarDadosValidacao.buscarOuFalhar(id);

        return new FuncionarioResponseDTO(
                entity.getId(),
                entity.getNomeCompleto(),
                entity.getCpf(),
                entity.getDataNascimento(),
                entity.getMatricula(),
                entity.getEmailCorporativo(),
                entity.getCargo(),
                entity.getSetor(),
                entity.getDataCadastro(),
                entity.getDataAtualizacao());
    }

    public FuncionarioEntity atualizarInformacoes(Integer id, AtualizacaoDTO dto){
        atualizarDadosValidacao.validarExisteFuncionario(id);

        FuncionarioEntity funcionario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário com id " + id + " não encontrado"));

        funcionario.setNomeCompleto(dto.getNomeCompleto());
        funcionario.setCargo(dto.getCargo());
        funcionario.setSetor(dto.getSetor());

        return repository.save(funcionario);
    }

    public void deletarFuncionario(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Funcionário com id " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }
}

