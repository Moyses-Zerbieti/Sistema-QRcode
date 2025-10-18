package com.codigoQR.sistemQrCode.serviceTest;

import com.codigoQR.sistemQrCode.dto.UsuarioRequest;
import com.codigoQR.sistemQrCode.model.Usuario;
import com.codigoQR.sistemQrCode.repository.UsuarioRepository;
import com.codigoQR.sistemQrCode.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UsuarioService usuarioService;


    @Test
    void salvarUsuarioTest(){
        UsuarioRequest usuarioTest = new UsuarioRequest();
        usuarioTest.setLogin("admin");
        usuarioTest.setSenha("123");
        usuarioTest.setRoles(Collections.singletonList("ADMIN"));

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(UUID.randomUUID());
        usuarioSalvo.setLogin("admin");
        usuarioSalvo.setSenha("senhaCriptada123");
        usuarioTest.setRoles(Collections.singletonList("ADMIN"));

        when(encoder.encode("123")).thenReturn("senhaCriptada123");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        usuarioService.salvar(usuarioTest);

        assertEquals("encodedPassword123", usuarioTest.getSenha());
        assertEquals("admin", usuarioTest.getLogin());
        assertEquals(Collections.singletonList("ADMIN"), usuarioTest.getRoles());

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(encoder,times(1)).encode("123");
    }

    @Test
    void obterUsuarioPorIdTest(){
        Usuario usuarioTest = new Usuario();

        usuarioTest.setId(UUID.randomUUID());
        usuarioTest.setLogin("admin");
        usuarioTest.setSenha("123");
        usuarioTest.setRoles(Collections.singletonList("ADMIN"));

        when(usuarioRepository.findByLogin(usuarioTest.getLogin())).thenReturn(usuarioTest);

         Usuario retorno = usuarioService.ObterPorLogin("admin");

         assertNotNull(retorno);
         assertEquals("admin", retorno.getLogin());
         assertEquals("123", retorno.getSenha());
         assertEquals(Collections.singletonList("ADMIN"),retorno.getRoles());
        verify(usuarioRepository,times(1)).findByLogin(usuarioTest.getLogin());
    }

}