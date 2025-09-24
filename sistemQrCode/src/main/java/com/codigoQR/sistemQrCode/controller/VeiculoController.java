package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.dto.AtualizacaoVeiculoDTO;
import com.codigoQR.sistemQrCode.dto.VeiculoResponseDTO;
import com.codigoQR.sistemQrCode.dto.VeiculoResquest;
import com.codigoQR.sistemQrCode.genericController.GenericControllers;
import com.codigoQR.sistemQrCode.model.Veiculo;
import com.codigoQR.sistemQrCode.service.VeiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("veiculo")
public class VeiculoController implements GenericControllers {

    private VeiculoService veiculoService;

    public VeiculoController (VeiculoService veiculoService){
        this.veiculoService = veiculoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<?>salvar(@RequestBody VeiculoResquest veiculo){
        Veiculo salvo = veiculoService.salvarVeiculo(veiculo);
        URI location = gerarHeaderLocation(salvo.getId());
        return ResponseEntity
                .created(location)
                .body("Veículo cadastrado com Sucesso!");
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    public VeiculoResponseDTO consultarVeiculo(@PathVariable Integer id){
        return veiculoService.consultaVeiculo(id);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletarVeiculo(@PathVariable Integer id){
        veiculoService.deletarVeiculo(id);
    return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<?> atualizarDadosVeiculo(
            @PathVariable  Integer id,
            @RequestBody AtualizacaoVeiculoDTO veiculonovo){

        Veiculo novaPlaca = veiculoService.atualizarDadosVeiculo(id, veiculonovo);

        AtualizacaoVeiculoDTO response = new AtualizacaoVeiculoDTO(
                novaPlaca.getPlaca());
        return ResponseEntity.ok(response);
    }
}