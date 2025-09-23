package com.codigoQR.sistemQrCode.service;

import com.codigoQR.sistemQrCode.model.Funcionario;
import com.codigoQR.sistemQrCode.model.RegistroAcesso;
import com.codigoQR.sistemQrCode.repository.FuncionarioRepository;
import com.codigoQR.sistemQrCode.repository.RegistroAcessoRepository;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AcessoService {
    private final FuncionarioRepository funcionarioRepository;
    private final RegistroAcessoRepository registroAcessoRepository;

    public AcessoService(FuncionarioRepository funcionarioRepository, RegistroAcessoRepository registroAcessoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.registroAcessoRepository = registroAcessoRepository;
    }

    public Map<String, Object> processarEscaneamento(Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));

        LocalDateTime agora = LocalDateTime.now();
        Optional<RegistroAcesso> ultimoAcessoOpt = registroAcessoRepository.findUltimoAcesso(funcionario.getId());

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

        RegistroAcesso registroAcesso = new RegistroAcesso();
        registroAcesso.setDataHoraAcesso(agora);
        registroAcesso.setStatus(acessoNegado ? "NEGADO" : "AUTORIZADO");
        registroAcesso.setSetor(funcionario.getSetor());
        registroAcesso.setCargo(funcionario.getCargo());
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