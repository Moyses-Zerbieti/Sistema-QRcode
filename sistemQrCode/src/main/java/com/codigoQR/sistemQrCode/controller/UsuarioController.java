package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.dto.UsuarioRequest;
import com.codigoQR.sistemQrCode.genericController.GenericControllerUsuario;
import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
@Tag(name = "Usuários")
public class UsuarioController implements GenericControllerUsuario {

    private UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Salvar", description = "Cadastrar um novo usuário")
    @ApiResponse (responseCode = "201", description = "Usuário cadastrado com sucesso")
    public ResponseEntity<?> salvar(@RequestBody UsuarioRequest usuario){
        service.salvar(usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{login}")
    @PreAuthorize("hasAnyRole('ADMIN,STAFF','USER')")
    @Operation(summary = "Consulta", description = "Consulta de dados do Usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> consultaUsuario( @PathVariable String login){
        Usuario usuario = service.ObterPorLogin(login);
        if (usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("{login}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar", description = "Deletar um usuário do sistema")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    public ResponseEntity<Void> deletarUsuarioLogin(@PathVariable String login){
        service.deletarUsuarioPorLogin(login);
        return ResponseEntity.noContent().build();
    }
}