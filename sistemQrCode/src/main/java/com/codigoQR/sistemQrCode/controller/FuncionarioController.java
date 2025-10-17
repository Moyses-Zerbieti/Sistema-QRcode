package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.dto.AtualizacaoDTO;
import com.codigoQR.sistemQrCode.dto.ConsultaPortariaDTO;
import com.codigoQR.sistemQrCode.dto.FuncionarioRequest;
import com.codigoQR.sistemQrCode.dto.FuncionarioResponseDTO;
import com.codigoQR.sistemQrCode.genericController.GenericControllers;
import com.codigoQR.sistemQrCode.model.Funcionario;
import com.codigoQR.sistemQrCode.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("funcionario")
@Tag(name = "Funcionário")
public class FuncionarioController implements GenericControllers {
    private FuncionarioService service;
    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Cadastrar", description = "Cadastra um novo funcionário no sistema ")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Funcionário Cadastrado com sucesso"),
        @ApiResponse(responseCode = "409", description = "Já existe um funcionário com esses dados no sistema"),
        @ApiResponse(responseCode = "404", description = "Cargo ou Setor não existem na base de dados do sistema para cadastrar o funcionário")
    })
    public ResponseEntity<?> salvar(@Valid @RequestBody FuncionarioRequest funcionarioRequest) {
        FuncionarioResponseDTO salvo = service.salvar(funcionarioRequest);
        URI location = gerarHeaderLocation(salvo.getId());
        return ResponseEntity
                .created(location)
                .body("Funcionário cadastrado com sucesso! QR Code enviado para " + salvo.getEmailCorporativo());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Operation(summary = "Consulta", description = "Executa a busca de um funcionário no sistema pelo ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    public FuncionarioResponseDTO consultaId(@PathVariable Integer id){
        return service.consultaId(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Atulizar dados", description = "Atualiza os dados do funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Verifique se os IDs de Cargo, Setor e Funcionário existem no sistema"),
    })
    public ResponseEntity<FuncionarioResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AtualizacaoDTO dto){

        Funcionario atualizado = service.atualizarInformacoes(id,dto);

        FuncionarioResponseDTO response = new FuncionarioResponseDTO(
                atualizado.getId(),
                atualizado.getNomeCompleto(),
                atualizado.getCpf(),
                atualizado.getDataNascimento(),
                atualizado.getMatricula(),
                atualizado.getEmailCorporativo(),
                atualizado.getCargo() != null ? atualizado.getCargo().getId() : null,
                atualizado.getSetor() != null ? atualizado.getSetor().getId() : null,
                atualizado.getIdUsuario().getId(),
                atualizado.getDataCadastro(),
                atualizado.getDataAtualizacao()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta um funcionário do sistema usando o seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Funcionário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário com o ID informado não encontrado")
    })
    public ResponseEntity<?> deletarFuncionario(@PathVariable("id") Integer id){
            service.deletarFuncionario(id);
            return ResponseEntity.ok("Funcionário com id " + id + " foi deletado com sucesso.");
    }

    @GetMapping("/cpf/{cpf:.+}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Operation(summary = "Consulta via CPF", description = "Executa a busca de um Funcionário no sistema pelo CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário com CPF informado não encontrado no sistema")
    })
        public ConsultaPortariaDTO consultaPortaria (@PathVariable("cpf") String cpf){
         return  service.consultaPortariaCPF(cpf);
    }
}