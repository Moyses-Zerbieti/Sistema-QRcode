package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.genericController.GenericControllers;
import com.codigoQR.sistemQrCode.service.AcessoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("qrcode")
public class QrCodeController implements GenericControllers {
    private AcessoService acessoService;

    public QrCodeController(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("escanear/{id}")
    public ResponseEntity<?> escanearQrCode(@PathVariable Integer id){
        URI location = gerarHeaderLocation(id);
        return ResponseEntity.ok(acessoService.processarEscaneamento(id));
    }
}