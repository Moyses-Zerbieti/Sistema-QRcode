package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("usuario")
public class UsuarioController implements GenericControllerUSER{

    private UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody Usuario usuario){
        service.salvar(usuario);
        URI location = headerLocationUser(usuario.getId());
    }
}