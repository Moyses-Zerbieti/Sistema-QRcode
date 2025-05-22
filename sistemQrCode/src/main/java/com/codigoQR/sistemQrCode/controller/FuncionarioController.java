package com.codigoQR.sistemQrCode.controller;


import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.service.ServiceCadastro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("funcionario")
public class FuncionarioController {

    private ServiceCadastro service;
    public FuncionarioController(ServiceCadastro service) {
        this.service = service;
    }

    @PostMapping
    public FuncionarioEntity salvar (@RequestBody FuncionarioEntity novoFuncionarioEntity){
        return service.salvar(novoFuncionarioEntity);
    }

    @PutMapping ("{id}")
    public FuncionarioEntity atualizarInformacoes(@PathVariable ("id") Integer id, @RequestBody FuncionarioEntity funcionarioEntity){
        funcionarioEntity.setId(id);
        return service.atualizarInformacoes(funcionarioEntity);
    }

    @GetMapping
    public FuncionarioEntity consultaId(@RequestParam("id") Integer id){
        return service.consultaId(id);
    }
}
