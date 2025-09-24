package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.dto.AtualizacaoVeiculoDTO;
import com.codigoQR.sistemQrCode.dto.FuncionarioResponseVeiculoDTO;
import com.codigoQR.sistemQrCode.dto.VeiculoResponseDTO;
import com.codigoQR.sistemQrCode.dto.VeiculoResquest;
import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.model.Funcionario;
import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.model.Veiculo;
import com.codigoQR.sistemQrCode.repository.FuncionarioRepository;
import com.codigoQR.sistemQrCode.repository.VeiculoRepository;
import com.codigoQR.sistemQrCode.security.SecurityService;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService{

    private VeiculoRepository veiculoRepository;
    private CadastroValidacao validacao;
    private AtualizarDadosValidacao atualizarValidacao;
    private SecurityService securityService;
    private FuncionarioRepository funcionarioRepository;

    public VeiculoService(
            VeiculoRepository veiculoRepository,
            CadastroValidacao validacao,
            AtualizarDadosValidacao atualizarValidacao,
            SecurityService securityService,
            FuncionarioRepository funcionarioRepository){
        this.veiculoRepository = veiculoRepository;
        this.validacao = validacao;
        this.atualizarValidacao = atualizarValidacao;
        this.securityService = securityService;
        this.funcionarioRepository = funcionarioRepository;
    }

    public Veiculo salvarVeiculo(VeiculoResquest dto){
        Veiculo novoVeiculo = new Veiculo();

        novoVeiculo.setModeloCarro(dto.getModeloCarro());
        novoVeiculo.setAnoModelo(dto.getAnoModelo());
        novoVeiculo.setPlaca(dto.getPlaca());

        Funcionario funcionario = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(()-> new ResourceNotFoundException("Funcionario não encontrado"));
        novoVeiculo.setFuncionarioId(funcionario);

        validacao.validarCadastroVeiculo(novoVeiculo);

        if (novoVeiculo.getFuncionarioId()== null){
            throw new ResourceNotFoundException("É necessário vincular um funcionário ao veículo");
        }

        Usuario usuario = securityService.obterUsuarioLogado();
        novoVeiculo.setUsuario(usuario);
        return veiculoRepository.save(novoVeiculo);
    }

    public VeiculoResponseDTO consultaVeiculo(Integer id){
        Veiculo entity = atualizarValidacao.buscarOuFalharVeiculo(id);

        Funcionario funcionario = entity.getFuncionarioId();
        FuncionarioResponseVeiculoDTO funcionarioVeiculoDTO = new FuncionarioResponseVeiculoDTO(
                funcionario.getId(),
                funcionario.getNomeCompleto(),
                funcionario.getCpf(),
                funcionario.getDataNascimento(),
                funcionario.getMatricula(),
                funcionario.getEmailCorporativo());

        return new VeiculoResponseDTO(
                entity.getId(),
                entity.getModeloCarro(),
                entity.getAnoModelo(),
                entity.getPlaca(),
                funcionarioVeiculoDTO,
                entity.getDataCadastroVeiculo(),
                entity.getDataAtualizacaoVeiculo());
    }

    public Veiculo atualizarDadosVeiculo(Integer id, AtualizacaoVeiculoDTO dto){
        atualizarValidacao.validarExisteVeiculo(id);

        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Veículo não encontrado"));

        if (dto.getPlaca() != null && !dto.getPlaca().isBlank()){
            veiculo.setPlaca(dto.getPlaca());
            atualizarValidacao.validarPlacasDuplas(veiculo);
        }

        Usuario usuario = securityService.obterUsuarioLogado();
        veiculo.setUsuario(usuario);
        return veiculoRepository.save(veiculo);
    }

    public void  deletarVeiculo(Integer id){
        if(!veiculoRepository.existsById(id)){
            throw new ResourceNotFoundException("Veiculo com id " + id + " não econtrado");
        }
        Usuario usuario = securityService.obterUsuarioLogado();
        veiculoRepository.deleteById(id);
    }
}