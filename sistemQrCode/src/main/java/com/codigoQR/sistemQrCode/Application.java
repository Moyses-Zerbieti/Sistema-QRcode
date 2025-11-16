package com.codigoQR.sistemQrCode;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
		builder.bannerMode(Banner.Mode.OFF);
		builder.run(args);

		ConfigurableApplicationContext applicationContext = builder.context();
		ApplicationName name = applicationContext.getBean(ApplicationName.class);
		name.imprimirProjeto();
	}
}