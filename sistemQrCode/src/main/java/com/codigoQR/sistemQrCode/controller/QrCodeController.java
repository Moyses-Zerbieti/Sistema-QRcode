package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.service.ServiceAcesso;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("qrcode")
public class QrCodeController implements GenericController {
    private ServiceAcesso serviceAcesso;

    public QrCodeController(ServiceAcesso serviceAcesso) {
        this.serviceAcesso = serviceAcesso;
    }

    @PostMapping("escanear/{id}")
    public ResponseEntity<?> escanearQrCode(@PathVariable Integer id){
        URI location =gerarHeaderLocation(id);
        return ResponseEntity.ok(serviceAcesso.processarEscaneamento(id));
    }
}