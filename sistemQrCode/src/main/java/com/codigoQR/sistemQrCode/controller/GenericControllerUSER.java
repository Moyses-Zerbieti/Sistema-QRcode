package com.codigoQR.sistemQrCode.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericControllerUSER {

    default URI headerLocationUser(UUID id){
        return ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("{id}")
            .buildAndExpand(id)
            .toUri();
    }
}