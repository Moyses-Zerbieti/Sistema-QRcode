package com.codigoQR.sistemQrCode.config;

import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class UserTest implements CommandLineRunner {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder encoder;

    public UserTest(UsuarioRepository usuarioRepository,
                    PasswordEncoder encoder){
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String login = "adminTest";
        if (!usuarioRepository.existsByLogin(login)){
            Usuario adminTest = new Usuario();
            adminTest.setLogin(login);
            adminTest.setSenha(encoder.encode("1234"));
            adminTest.setRoles(List.of("ADMIN"));
            usuarioRepository.save(adminTest);
            System.out.println("Usuario para Testes Criado com sucesso");
        }
    }
}