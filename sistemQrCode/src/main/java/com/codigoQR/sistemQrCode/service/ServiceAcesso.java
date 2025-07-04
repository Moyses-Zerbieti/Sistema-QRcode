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
import java.util.Optional;

@Service
public class ServiceAcesso {
    private final FuncionariosRepository funcionariosRepository;
    private final RegistroAcessoRepository registroAcessoRepository;

    public ServiceAcesso(FuncionariosRepository funcionariosRepository, RegistroAcessoRepository registroAcessoRepository) {
        this.funcionariosRepository = funcionariosRepository;
        this.registroAcessoRepository = registroAcessoRepository;
    }

    public Map<String, Object> processarEscaneamento(Integer idFuncionario) {
        FuncionarioEntity funcionario = funcionariosRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));

        LocalDateTime agora = LocalDateTime.now();
        Optional<RegistroAcessoEntity> ultimoAcessoOpt = registroAcessoRepository.findUltimoAcesso(funcionario.getId());

        boolean acessoNegado = false;
        long minutosDesdeUltimoAcesso = 0;

        if (ultimoAcessoOpt.isPresent()) {
            LocalDateTime ultimoAcesso = ultimoAcessoOpt.get().getDataHoraAcesso();
            minutosDesdeUltimoAcesso = Duration.between(ultimoAcesso, agora).toMinutes();

            System.out.println("Último acesso foi há " + minutosDesdeUltimoAcesso + " minutos.");
            acessoNegado = minutosDesdeUltimoAcesso < 120;
        } else {
            System.out.println("Nenhum acesso anterior encontrado.");
        }

        RegistroAcessoEntity registroAcesso = new RegistroAcessoEntity();
        registroAcesso.setDataHoraAcesso(agora);
        registroAcesso.setStatus(acessoNegado ? "NEGADO" : "AUTORIZADO");
        registroAcesso.setSetor(funcionario.getSetor());
        registroAcesso.setFuncionario(funcionario);
        registroAcessoRepository.save(registroAcesso);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("nome", funcionario.getNomeCompleto());
        resposta.put("cargo", funcionario.getCargo());
        resposta.put("setor", funcionario.getSetor());
        resposta.put("status", registroAcesso.getStatus());

        if (acessoNegado) {
            long tempoRestante = 120 - minutosDesdeUltimoAcesso;
            resposta.put("mensagem", "Acesso negado! Aguarde " + tempoRestante + " minutos para passar novamente.");
        } else {
            resposta.put("mensagem", "Acesso autorizado para " + funcionario.getNomeCompleto());
        }

        return resposta;
    }
}