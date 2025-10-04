package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.genericController.GenericControllerUsuario;
import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("usuario")
public class UsuarioController implements GenericControllerUsuario {

    private UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void salvar(@RequestBody Usuario usuario){
        service.salvar(usuario);
        URI location = headerLocationUsuario(usuario.getId());
    }

    @GetMapping("{login}")
    @PreAuthorize("hasAnyRole('ADMIN,STAFF','USER')")
    public ResponseEntity<Usuario> consultaUsuario( @PathVariable String login){
        Usuario usuario = service.ObterPorLogin(login);
        if (usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("{login}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarUsuarioLogin(@PathVariable String login){
        service.deletarUsuarioPorLogin(login);
        return ResponseEntity.noContent().build();
    }
}