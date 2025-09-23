package com.codigoQR.sistemQrCode.controller;

import com.codigoQR.sistemQrCode.dto.AtualizacaoDTO;
import com.codigoQR.sistemQrCode.dto.ConsultaPortariaDTO;
import com.codigoQR.sistemQrCode.dto.FuncionarioRequest;
import com.codigoQR.sistemQrCode.dto.FuncionarioResponseDTO;
import com.codigoQR.sistemQrCode.genericController.GenericControllers;
import com.codigoQR.sistemQrCode.model.Funcionario;
import com.codigoQR.sistemQrCode.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("funcionario")
public class FuncionarioController implements GenericControllers {
    private FuncionarioService service;
    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<?> salvar(@Valid @RequestBody FuncionarioRequest funcionarioRequest) {
        Funcionario salvo = service.salvar(funcionarioRequest);
        URI location = gerarHeaderLocation(salvo.getId());
        return ResponseEntity
                .created(location)
                .body("Funcionário cadastrado com sucesso! QR Code enviado para " + salvo.getEmailCorporativo());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
    public FuncionarioResponseDTO consultaId(@PathVariable Integer id){
        return service.consultaId(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<FuncionarioResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AtualizacaoDTO dto){

        Funcionario atualizado = service.atualizarInformacoes(id,dto);

        FuncionarioResponseDTO response = new FuncionarioResponseDTO(
                atualizado.getId(),
                atualizado.getNomeCompleto(),
                atualizado.getCpf(),
                atualizado.getDataNascimento(),
                atualizado.getMatricula(),
                atualizado.getEmailCorporativo(),
                atualizado.getCargo() != null ? atualizado.getCargo().getNomeCargo() : null,
                atualizado.getSetor() != null ? atualizado.getSetor().getNomeSetor() : null,
                atualizado.getDataCadastro(),
                atualizado.getDataAtualizacao()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletarFuncionario(@PathVariable("id") Integer id){
            service.deletarFuncionario(id);
            return ResponseEntity.ok("Funcionário com id " + id + " foi deletado com sucesso.");
    }

    @GetMapping("/cpf/{cpf:.+}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','USER')")
        public ConsultaPortariaDTO consultaPortaria (@PathVariable("cpf") String cpf){
         return  service.consultaPortariaCPF(cpf);
    }
}