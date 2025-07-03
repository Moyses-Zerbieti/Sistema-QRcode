package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.dto.FuncionarioRequest;
import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.service.ServiceCadastro;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("funcionario")
public class FuncionarioController implements GenericController{
    private ServiceCadastro service;
    public FuncionarioController(ServiceCadastro service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@Valid @RequestBody FuncionarioRequest funcionarioRequest) {
        FuncionarioEntity salvo = service.salvar(funcionarioRequest);
        URI location = gerarHeaderLocation(salvo.getId());
        return ResponseEntity
                .created(location)
                .body("Funcionário cadastrado com sucesso! QR Code enviado para " + salvo.getEmailCorporativo());
    }

    @GetMapping("{id}")
    public FuncionarioEntity consultaId(@PathVariable Integer id){
        return service.consultaId(id);
    }

    @PutMapping
    public ResponseEntity<FuncionarioEntity> atualizar(@Valid @RequestBody FuncionarioEntity funcionario){
        FuncionarioEntity atualizado = service.atualizarInformacoes(funcionario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarFuncionario(@PathVariable("id") Integer id){
            service.deletarFuncionario(id);
            return ResponseEntity.ok("Funcionário com id " + id + " foi deletado com sucesso.");
    }
}
