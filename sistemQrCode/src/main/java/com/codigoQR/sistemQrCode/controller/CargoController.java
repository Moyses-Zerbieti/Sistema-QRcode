package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.dto.CargoRequest;
import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.genericController.GenericControllers;
import com.codigoQR.sistemQrCode.model.Cargo;
import com.codigoQR.sistemQrCode.service.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cargo")
@Tag(name = "Cargo")
public class CargoController implements GenericControllers {
    private CargoService cargoService;

    public CargoController (CargoService cargoService){
        this.cargoService = cargoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(summary = "Cadastrar", description = "Cadastra um novo cargo no sistema")
    @ApiResponse(responseCode = "201", description = "Cargo cadastrado com sucesso")
    public ResponseEntity<?> salvar(@RequestBody CargoRequest cargo){
        CargoRequest cargoSalvo = cargoService.salvarCargo(cargo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Operation(summary = "Consulta", description = "Executa uma busca pelo ID do cargo esperado no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo encontrado com sucesoo"),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado")
    })
    public ResponseEntity<Cargo> consultaId(@PathVariable("id") Integer id){
        return cargoService.obterPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta um cargo do sistma")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cargo deletado do sistema com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado no sistema")
    })
    public ResponseEntity<Object>deletarCargo(@PathVariable("id") Integer id){
        try{
            cargoService.excluirCargo(id);
            return ResponseEntity.noContent().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}