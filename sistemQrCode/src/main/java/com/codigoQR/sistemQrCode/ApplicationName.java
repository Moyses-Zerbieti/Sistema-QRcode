package com.codigoQR.sistemQrCode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationName {

    @Value("${spring.application.name}")
    private String nome;
        public void imprimirProjeto(){
            System.out.println("Desenvolvido por Moyses Zerbieti: " + nome);
        }
}

