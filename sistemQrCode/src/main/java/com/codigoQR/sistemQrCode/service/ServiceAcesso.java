package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.model.FuncionarioEntity;
import com.codigoQR.sistemQrCode.model.RegistroAcessoEntity;
import com.codigoQR.sistemQrCode.repository.FuncionariosRepository;
import com.codigoQR.sistemQrCode.repository.RegistroAcessoRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceAcesso {
    private final FuncionariosRepository funcionariosRepository;
    private final RegistroAcessoRepository registroAcessoRepository;

    public ServiceAcesso(
            FuncionariosRepository funcionariosRepository,RegistroAcessoRepository registroAcessoRepository){

        this.funcionariosRepository = funcionariosRepository;
        this.registroAcessoRepository = registroAcessoRepository;
    }

    public Map<String, Object> processarEscaneamento(Integer idFuncionario){
        FuncionarioEntity funcionario = funcionariosRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));

        boolean acessoNegado = registroAcessoRepository.findUltimoAcesso(funcionario.getId())
                .filter(reg -> Duration.between(reg.getDataHoraAcesso(), LocalDateTime.now())
                .toMinutes() < 120)
                .isPresent();

        RegistroAcessoEntity registroAcesso = new RegistroAcessoEntity();
        registroAcesso.setDataHoraAcesso(LocalDateTime.now());
        registroAcesso.setStatus(acessoNegado ? "NEGADO" : "AUTORIZADO");
        registroAcesso.setSetor(funcionario.getSetor());
        registroAcesso.setFuncionario(funcionario);
        registroAcessoRepository.save(registroAcesso);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("nome", funcionario.getNomeCompleto());
        resposta.put("cargo", funcionario.getCargo());
        resposta.put("setor", funcionario.getSetor());
        resposta.put("status", registroAcesso.getStatus());
        resposta.put("mensagem", acessoNegado ?
                "Acesso negado! Aguarde o tempo mínimo para passar!." :
                "Acesso autorizado para " + funcionario.getNomeCompleto());
        return resposta;
    }
}
