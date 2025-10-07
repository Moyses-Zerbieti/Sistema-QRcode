package com.codigoQR.sistemQrCode.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Sistema de Cadastro TAG-ID",
                version = "v1.1",
                contact = @Contact (
                        name = "Moyses Zerbieti",
                        email = "zerbietimoyses@gmail.com",
                        url = "https://www.linkedin.com/in/moyses-zerbieti/"
                )
        )
)
public class OpenApiConfiguration {
}
