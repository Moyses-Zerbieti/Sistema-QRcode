package com.codigoQR.sistemQrCode.controller;


import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.service.ServiceCadastro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("funcionario")
public class FuncionarioController {
    private ServiceCadastro service;
    public FuncionarioController(ServiceCadastro service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> salvar (@RequestBody FuncionarioEntity novoFuncionarioEntity){
            FuncionarioEntity salvo = service.salvar(novoFuncionarioEntity);
            return ResponseEntity.status(201)
                    .body("Funcionário cadastrado com sucesso! QR Code enviado para " + salvo.getEmailCorporativo());
    }

    @GetMapping
    public FuncionarioEntity consultaId(@RequestParam("id") Integer id){
        return service.consultaId(id);
    }

    @PutMapping
    public ResponseEntity<FuncionarioEntity> atualizar(@RequestBody FuncionarioEntity funcionario){
        FuncionarioEntity atualizado = service.atualizarInformacoes(funcionario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarFuncionario(@PathVariable("id") Integer id){
            service.deletarFuncionario(id);
            return ResponseEntity.ok("Funcionário com id " + id + " foi deletado com sucesso.");
    }
}
