package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.dto.AtualizacaoVeiculoDTO;
import com.codigoQR.sistemQrCode.dto.VeiculoResponseDTO;
import com.codigoQR.sistemQrCode.dto.VeiculoResquest;
import com.codigoQR.sistemQrCode.genericController.GenericControllers;
import com.codigoQR.sistemQrCode.model.Veiculo;
import com.codigoQR.sistemQrCode.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("veiculo")
@Tag(name = "Veículo")
public class VeiculoController implements GenericControllers {

    private VeiculoService veiculoService;

    public VeiculoController (VeiculoService veiculoService){
        this.veiculoService = veiculoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(summary = "Cadastrar", description = "Realiza o cadastro de um veículo no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veículo cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Já existe um veículo com esses dados no sistema"),
            @ApiResponse(responseCode = "404", description = "Funcionario com o ID informado não foi cadastrado no sistema ")
    })
    public ResponseEntity<?>salvar(@RequestBody VeiculoResquest veiculo){
        Veiculo salvo = veiculoService.salvarVeiculo(veiculo);
        URI location = gerarHeaderLocation(salvo.getId());
        return ResponseEntity
                .created(location)
                .body("Veículo cadastrado com Sucesso!");
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Operation(summary = "Consultar", description = "Executa a busca de um veículo no sistema pelo ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veículo encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    public VeiculoResponseDTO consultarVeiculo(@PathVariable Integer id){
        return veiculoService.consultaVeiculo(id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta um veículo do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Veículo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Véiculo não cadastrado no sistema")
    })
    public ResponseEntity<?> deletarVeiculo(@PathVariable Integer id){
        veiculoService.deletarVeiculo(id);
    return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(summary = "Atulizar dados", description = "Atualiza os dados do veículo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "409", description = "Já existe um veículo com esses dados no sistema"),
            @ApiResponse(responseCode = "404", description = "Veículo não cadastrado no sistema para alteração")
    })
    public ResponseEntity<?> atualizarDadosVeiculo(
            @PathVariable  Integer id,
            @RequestBody AtualizacaoVeiculoDTO veiculonovo){

        Veiculo novaPlaca = veiculoService.atualizarDadosVeiculo(id, veiculonovo);

        AtualizacaoVeiculoDTO response = new AtualizacaoVeiculoDTO(
                novaPlaca.getPlaca());
        return ResponseEntity.ok(response);
    }
}