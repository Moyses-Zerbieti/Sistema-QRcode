package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.exception.ResourceNotFoundException;
import com.codigoQR.sistemQrCode.genericController.GenericControllers;
import com.codigoQR.sistemQrCode.model.Cargo;
import com.codigoQR.sistemQrCode.service.CargoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("cargo")
public class CargoController implements GenericControllers {
    private CargoService cargoService;

    public CargoController (CargoService cargoService){
        this.cargoService = cargoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<Object> salvar(@RequestBody Cargo cargo){
        Cargo cargoSalvo = cargoService.salvarCargo(cargo);
        URI location = gerarHeaderLocation(cargoSalvo.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    public ResponseEntity<Cargo> consultaId(@PathVariable("id") Integer id){
        return cargoService.obterPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object>deletarCargo(@PathVariable("id") Integer id){
        try{
            cargoService.excluirCargo(id);
            return ResponseEntity.noContent().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}