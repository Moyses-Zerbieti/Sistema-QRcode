package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.dto.AtualizacaoDTO;
import com.codigoQR.sistemQrCode.dto.FuncionarioRequest;
import com.codigoQR.sistemQrCode.dto.FuncionarioResponseDTO;
import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.service.ServiceFuncionarios;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("funcionario")
public class FuncionarioController implements GenericController{
    private ServiceFuncionarios service;
    public FuncionarioController(ServiceFuncionarios service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> salvar(@Valid @RequestBody FuncionarioRequest funcionarioRequest) {
        FuncionarioEntity salvo = service.salvar(funcionarioRequest);
        URI location = gerarHeaderLocation(salvo.getId());
        return ResponseEntity
                .created(location)
                .body("Funcionário cadastrado com sucesso! QR Code enviado para " + salvo.getEmailCorporativo());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    public FuncionarioResponseDTO consultaId(@PathVariable Integer id){
        return service.consultaId(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<FuncionarioResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AtualizacaoDTO dto){

        FuncionarioEntity atualizado = service.atualizarInformacoes(id,dto);

        FuncionarioResponseDTO response = new FuncionarioResponseDTO(
                atualizado.getId(),
                atualizado.getNomeCompleto(),
                atualizado.getCpf(),
                atualizado.getDataNascimento(),
                atualizado.getMatricula(),
                atualizado.getEmailCorporativo(),
                atualizado.getCargo(),
                atualizado.getSetor(),
                atualizado.getDataCadastro(),
                atualizado.getDataAtualizacao()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletarFuncionario(@PathVariable("id") Integer id){
            service.deletarFuncionario(id);
            return ResponseEntity.ok("Funcionário com id " + id + " foi deletado com sucesso.");
    }
}
