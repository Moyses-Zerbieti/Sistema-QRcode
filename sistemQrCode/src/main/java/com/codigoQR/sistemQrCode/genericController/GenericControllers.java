package com.codigoQR.sistemQrCode.genericController;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

public interface GenericControllers {

    default URI gerarHeaderLocation(Integer id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(id)
                .toUri();
    }
}