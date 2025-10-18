package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.dto.SetorRequest;
import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.genericController.GenericControllers;
import com.codigoQR.sistemQrCode.model.Setor;
import com.codigoQR.sistemQrCode.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("setor")
@Tag(name = "Setor")
public class SetorController implements GenericControllers {

    private SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(summary = "Cadastrar", description = "Cadastra um novo setor no sistema")
    @ApiResponse(responseCode = "201", description = "Setor cadastrado com sucesso")
    public ResponseEntity<?> salvar(@RequestBody SetorRequest setor){
        SetorRequest salvarSetor = setorService.salvarSetor(setor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    @Operation(summary = "Consulta",description = "Executa uma busca pelo ID do setor esperado no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Setor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Setor não encontrado")
    })
    public ResponseEntity<Setor> consultaId(@PathVariable("id") Integer id){
        return setorService.obterPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta um setor do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Setor deletado com sucesso"),
            @ApiResponse(responseCode = "404",description = "Setor não encontrado no sistema")
    })
    public ResponseEntity<Object>deletarCargo(@PathVariable("id") Integer id){
        try{
            setorService.excluirSetor(id);
            return ResponseEntity.noContent().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}