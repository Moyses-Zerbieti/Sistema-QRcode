package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.dto.AtualizacaoDTO;
import com.codigoQR.sistemQrCode.dto.FuncionarioResponseDTO;
import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.dto.FuncionarioRequest;
import com.codigoQR.sistemQrCode.model.Cargo;
import com.codigoQR.sistemQrCode.model.Funcionario;
import com.codigoQR.sistemQrCode.model.Setor;
import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.repository.CargoRepository;
import com.codigoQR.sistemQrCode.repository.FuncionarioRepository;
import com.codigoQR.sistemQrCode.repository.SetorRepository;
import com.codigoQR.sistemQrCode.security.SecurityService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ServiceFuncionario {

    private FuncionarioRepository repository;
    private CadastroValidacao validacao;
    private EmailFuncionario emailFuncionario;
    private AtualizarDadosValidacao atualizarDadosValidacao;
    private QrCodeGenerator qrCodeGenerator;
    private SecurityService securityService;
    private CargoRepository cargoRepository;
    private SetorRepository setorRepository;

    public ServiceFuncionario(FuncionarioRepository repository,
                              CadastroValidacao validacao,
                              EmailFuncionario emailFuncionario,
                              AtualizarDadosValidacao atualizarDadosValidacao,
                              QrCodeGenerator qrCodeGenerator,
                              SecurityService securityService,
                              CargoRepository cargoRepository,
                              SetorRepository setorRepository) {
        this.repository = repository;
        this.validacao = validacao;
        this.emailFuncionario = emailFuncionario;
        this.atualizarDadosValidacao = atualizarDadosValidacao;
        this.qrCodeGenerator = qrCodeGenerator;
        this.securityService = securityService;
        this.cargoRepository = cargoRepository;
        this.setorRepository = setorRepository;
    }

    public Funcionario salvar(FuncionarioRequest dto) {
        Funcionario novoFuncionario = new Funcionario();

        novoFuncionario.setNomeCompleto(dto.getNomeCompleto());
        novoFuncionario.setCpf(dto.getCpf());
        novoFuncionario.setDataNascimento(dto.getDataNascimento());
        novoFuncionario.setEmailCorporativo(dto.getEmailCorporativo());

        Cargo cargo = cargoRepository.findById(dto.getCargoId())
                        .orElseThrow(()-> new RuntimeException("Cargo não encontrado"));
        novoFuncionario.setCargo(cargo);

        Setor setor = setorRepository.findById(dto.getSetorId())
                        .orElseThrow(()-> new RuntimeException("Setor não encontrado"));
        novoFuncionario.setSetor(setor);

        novoFuncionario.setMatricula(UUID.randomUUID());

        validacao.validarFuncionario(novoFuncionario);
        Usuario usuario = securityService.obterUsuarioLogado();
        novoFuncionario.setIdUsuario(usuario);

        Funcionario salvo = repository.save(novoFuncionario);

        try {
            String textoQrCode = """
                    Nome: %s
                    Cargo: %s
                    Setor: %s
                    """.formatted(
                    salvo.getNomeCompleto(),
                    salvo.getCargo().getNomeCargo(),
                    salvo.getSetor().getNomeSetor()
                    );
            byte[] imagemQrCode = qrCodeGenerator.gerarQrCode(textoQrCode);
            emailFuncionario.enviarQrCodePorEmail(salvo.getEmailCorporativo(), imagemQrCode);
        } catch (Exception e) {
            System.err.println("Erro ao gerar/enviar QR CODE: " + e.getMessage());
        }
        return salvo;
    }

    public FuncionarioResponseDTO consultaId(Integer id) {
        Funcionario entity = atualizarDadosValidacao.buscarOuFalhar(id);

        return new FuncionarioResponseDTO(
                entity.getId(),
                entity.getNomeCompleto(),
                entity.getCpf(),
                entity.getDataNascimento(),
                entity.getMatricula(),
                entity.getEmailCorporativo(),
                entity.getCargo().getNomeCargo(),
                entity.getSetor().getNomeSetor(),
                entity.getDataCadastro(),
                entity.getDataAtualizacao());
    }

    public Funcionario atualizarInformacoes(Integer id, AtualizacaoDTO dto){
        atualizarDadosValidacao.validarExisteFuncionario(id);

        Funcionario funcionario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário com id " + id + " não encontrado"));

        funcionario.setNomeCompleto(dto.getNomeCompleto());

        Cargo cargo = cargoRepository.findById(dto.getCargoId())
                        .orElseThrow(()-> new RuntimeException("Cargo não encontrado"));
        funcionario.setCargo(cargo);


        Setor setor = setorRepository.findById(dto.getSetorId())
                        .orElseThrow(()-> new RuntimeException("Setor não encontrado"));
        funcionario.setSetor(setor);

        return repository.save(funcionario);
    }

    public void deletarFuncionario(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Funcionário com id " + id + " não encontrado.");
        }
        Usuario usuario = securityService.obterUsuarioLogado();
        repository.deleteById(id);
    }
}