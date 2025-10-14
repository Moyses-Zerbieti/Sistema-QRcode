package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.genericController.GenericControllers;
import com.codigoQR.sistemQrCode.service.AcessoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("qrcode")
@Tag(name = "Escaneamento Qr-Code")
public class QrCodeController implements GenericControllers {
    private AcessoService acessoService;

    public QrCodeController(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("escanear/{id}")
    @Operation(summary = "Processar Escaneamento", description = "valida e registra o escaneamento de um QR Code associado a um funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Escaneamento processado com sucesso"),
    })
    public ResponseEntity<?> escanearQrCode(@PathVariable Integer id){
        URI location = gerarHeaderLocation(id);
        return ResponseEntity.ok(acessoService.processarEscaneamento(id));
    }
}