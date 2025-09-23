package com.codigoQR.sistemQrCode.genericController;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericControllerUsuario {

    default URI headerLocationUsuario(UUID id){
        return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("{id}")
            .buildAndExpand(id)
            .toUri();
    }
}