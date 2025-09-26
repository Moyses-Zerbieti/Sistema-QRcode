package com.codigoQR.sistemQrCode.serviceTest;

import com.codigoQR.sistemQrCode.dto.AtualizacaoDTO;
import com.codigoQR.sistemQrCode.dto.FuncionarioRequest;
import com.codigoQR.sistemQrCode.dto.FuncionarioResponseDTO;
import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.model.*;
import com.codigoQR.sistemQrCode.repository.CargoRepository;
import com.codigoQR.sistemQrCode.repository.FuncionarioRepository;
import com.codigoQR.sistemQrCode.repository.SetorRepository;
import com.codigoQR.sistemQrCode.security.SecurityService;
import com.codigoQR.sistemQrCode.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List; import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @Mock
    FuncionarioRepository funcionarioRepository;
    @Mock
    CadastroValidacao cadastroValidacao;
    @Mock
    AtualizarDadosValidacao atualizarDadosValidacao;
    @Mock
    SecurityService securityService;
    @Mock
    QrCodeGenerator qrCodeGenerator;
    @Mock
    EmailFuncionario emailFuncionario;
    @Mock
    CargoRepository cargoRepository;
    @Mock
    SetorRepository setorRepository;
    @InjectMocks
    FuncionarioService funcionarioService;

    @Test
    void salvarFuncionarioTest(){
        Cargo cargoDto = new Cargo();
        cargoDto.setId(1);
        cargoDto.setNomeCargo("Vendedor");
        Setor setorDto = new Setor();
        setorDto.setId(1);
        setorDto.setNomeSetor("Comercial");
        Usuario usuarioDto = new Usuario();
        usuarioDto.setId(UUID.randomUUID());
        usuarioDto.setLogin("user");
        usuarioDto.setSenha("123");

        RegistroAcesso acesso = new RegistroAcesso();
        acesso.setId(10); acesso.setStatus("AUTORIZADO");
        acesso.setCargo(cargoDto); acesso.setSetor(setorDto);
        acesso.setDataHoraAcesso(LocalDateTime.now());

        FuncionarioRequest dto = new FuncionarioRequest();
        dto.setNomeCompleto("Moyses Zerbieti");
        dto.setCpf("001.100.010-10");
        dto.setDataNascimento(LocalDate.of(2002,1,6));
        dto.setEmailCorporativo("moyseszerbieti@gmail.com");
        dto.setCargoId(cargoDto.getId()); dto.setSetorId(setorDto.getId());
        dto.setIdUsuario(usuarioDto);

        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setId(1);
        novoFuncionario.setNomeCompleto(dto.getNomeCompleto());
        novoFuncionario.setCpf(dto.getCpf());
        novoFuncionario.setDataNascimento(dto.getDataNascimento());
        novoFuncionario.setMatricula(UUID.randomUUID());
        novoFuncionario.setEmailCorporativo(dto.getEmailCorporativo());
        novoFuncionario.setCargo(cargoDto);
        novoFuncionario.setSetor(setorDto);
        novoFuncionario.setIdUsuario(usuarioDto);
        novoFuncionario.setRegistroAcesso(List.of(acesso));

        when(cargoRepository.findById(1)).thenReturn(Optional.of(cargoDto));
        when(setorRepository.findById(1)).thenReturn(Optional.of(setorDto));
        when(securityService.obterUsuarioLogado()).thenReturn(usuarioDto);
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(novoFuncionario);

        Funcionario retorno = funcionarioService.salvar(dto);

        assertNotNull(retorno);
        assertEquals("Moyses Zerbieti", retorno.getNomeCompleto());
        assertEquals("Vendedor", retorno.getCargo().getNomeCargo());
        assertEquals("Comercial", retorno.getSetor().getNomeSetor());
        assertEquals(1, retorno.getRegistroAcesso().size());
        assertEquals("AUTORIZADO", retorno.getRegistroAcesso().get(0).getStatus());

        verify(cadastroValidacao,times(1)).validarFuncionario(any(Funcionario.class));
        verify(securityService,times(1)).obterUsuarioLogado();
        verify(funcionarioRepository,times(1)).save(any(Funcionario.class));
        verify(emailFuncionario,times(1)).enviarQrCodePorEmail(eq("moyseszerbieti@gmail.com"),any());
    }

    @Test
    void consultaFuncionarioPorIdTest(){
        Cargo cargoDto = new Cargo();
        cargoDto.setId(1);
        cargoDto.setNomeCargo("Vendedor");

        Setor setorDto = new Setor();
        setorDto.setId(1);
        setorDto.setNomeSetor("Comercial");
        Usuario usuarioDto = new Usuario();
        usuarioDto.setId(UUID.randomUUID());

        Funcionario entity = new Funcionario();
        entity.setNomeCompleto("Moyses Zerbieti");
        entity.setCpf("000.001.100-11");
        entity.setDataNascimento(LocalDate.of(2002,6,1));
        entity.setMatricula(UUID.randomUUID());
        entity.setEmailCorporativo("moyseszerbieti@gmail.com");
        entity.setCargo(cargoDto);
        entity.setSetor(setorDto);
        entity.setIdUsuario(usuarioDto);

        when(atualizarDadosValidacao.buscarOuFalhar(1)).thenReturn(entity);

        FuncionarioResponseDTO dto = funcionarioService.consultaId(1);
        assertNotNull(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getNomeCompleto(),dto.getNomeCompleto());
        assertEquals(entity.getCpf(), dto.getCpf());
        assertEquals(entity.getDataNascimento(),dto.getDataNascimento());
        assertEquals(entity.getMatricula(),dto.getMatricula());
        assertEquals(entity.getEmailCorporativo(),dto.getEmailCorporativo());
        assertEquals(entity.getCargo().getNomeCargo(),dto.getCargo());
        assertEquals(entity.getSetor().getNomeSetor(),dto.getSetor());

    }

    @Test
    void consultaFuncionarioPorIdNaoEncontradoTest(){

        when(atualizarDadosValidacao.buscarOuFalhar(99)).thenThrow(new ResourceNotFoundException("Funcionario com id 99 não encontrado"));

        assertThrows(ResourceNotFoundException.class,()->{
            funcionarioService.consultaId(99); });

        verify(atualizarDadosValidacao, times(1)).buscarOuFalhar(99); }

    @Test
    void atualizarDadosFuncionarioTest(){
        Cargo cargoDto = new Cargo();
        cargoDto.setId(1);
        cargoDto.setNomeCargo("Vendedor");

        Setor setorDto = new Setor();
        setorDto.setId(1);
        setorDto.setNomeSetor("Comercial");
        Usuario usuarioDto = new Usuario();
        usuarioDto.setId(UUID.randomUUID());

        Funcionario entity = new Funcionario();
        entity.setId(1);
        entity.setNomeCompleto("Moyses Zerbieti");
        entity.setCpf("000.001.100-11");
        entity.setDataNascimento(LocalDate.of(2002,6,1));
        entity.setMatricula(UUID.randomUUID());
        entity.setEmailCorporativo("moyseszerbieti@gmail.com");
        entity.setCargo(cargoDto);
        entity.setSetor(setorDto);
        entity.setIdUsuario(usuarioDto);

        AtualizacaoDTO dto = new AtualizacaoDTO();
        dto.setNomeCompleto("Moyses Martins");
        dto.setCargoId(entity.getCargo().getId());
        dto.setSetorId(entity.getSetor().getId());

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(entity));
        when(cargoRepository.findById(dto.getCargoId())).thenReturn(Optional.of(cargoDto));
        when(setorRepository.findById(dto.getSetorId())).thenReturn(Optional.of(setorDto));
        when(funcionarioRepository.save(any(Funcionario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Funcionario retorno = funcionarioService.atualizarInformacoes(1,dto);
        assertNotNull(entity); assertEquals("Moyses Martins", retorno.getNomeCompleto());
        assertEquals(cargoDto,retorno.getCargo());
        assertEquals(setorDto, retorno.getSetor()); verify(cargoRepository,times(1)).findById(dto.getCargoId());
        verify(setorRepository,times(1)).findById(dto.getSetorId());
        verify(funcionarioRepository,times(1)).findById(1);
        verify(funcionarioRepository,times(1)).save(any(Funcionario.class));
    }
}
