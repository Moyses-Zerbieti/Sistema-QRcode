# Tag-ID

## Sistema de Cadastro de Funcionários com QR Code

[![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)](https://github.com/seuusuario/seurepositorio)  
[![Java](https://img.shields.io/badge/Java-21+-blue)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)  
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-green)](https://spring.io/projects/spring-boot)  

---

## Descrição

### Este sistema foi desenvolvido com Java 21 e Spring Boot 3.4.5, e tem como objetivo facilitar a gestão de funcionários de uma empresa, desde o cadastro até o envio automático de um QR Code para identificação, via e-mail corporativo. Ele conta com uma API REST documentada com Swagger e testada com Postman, além de persistência de dados usando PostgreSQL e gerenciamento visual com pgAdmin.

---

## 🚧 Status do Projeto

O projeto está em desenvolvimento contínuo, com diversas melhorias sendo implementadas, como segurança, usabilidade e documentação.

---

## 🎯 Objetivos Principais

✅ Cadastro de funcionários (nome completo, CPF, e-mail corporativo, data de nascimento, cargoId e setorId de trabalho).

✅ Geração automática de QR Code exclusivo para cada funcionário.

✅ Envio do QR Code para o e-mail corporativo do funcionário após o cadastro.

✅ Listagem de todos os funcionários cadastrados.

✅ Atualização de dados do funcionário por ID.

✅ Recuperação de dados por ID.

✅ Autenticação do responsável pelos cadastros via token por e-mail.

✅ Documentação da API com Swagger.

✅ Geração automática de QR Code exclusivo

✅ Envio do QR Code por e-mail automaticamente

✅ Atualização e listagem de funcionários

✅ Autenticação por token enviado ao responsável via e-mail

✅ Documentação interativa da API com Swagger UI

---

## 💻 Tecnologias Utilizadas
Java 21

Spring Boot 3.4.5

Spring Web

Spring Data JPA

Spring Boot Mail (envio de e-mails)

ZXing (geração de QR Code)

PostgreSQL (banco de dados)

pgAdmin (gerenciador visual do banco)

Postman (testes da API)

Swagger UI (documentação interativa da API)

---

## 📂 Organização das Pastas

<pre><code>SistemQrCode/ 
  └──src 
      └── main/ 
          └── java/ 
              └── com/codigoQR/sistemQrCode/
                  ├─ common/             # Utilitários e classes comuns
                  ├─ config/             # Configurações da aplicação
                  ├─ controller/         # Endpoints REST
                  ├─ genericController/  # Controllers genéricos
                  ├─ dto/                # Objetos de transferência de dados
                  ├─ exception/          # Tratamento de exceções
                  ├─ model/              # Entidades do sistema
                  ├─ repository/         # Interfaces JPA
                  ├─ security/           # Configurações de segurança
                  └─ service/            # Regras de negócio
            └─ resources/
               ├─ application.yml
               └─ application-example.yml
         └─ test/
            └─ java/
               └─ com/codigoQR/sistemQrCode/
                 └─ serviceTest/        # Testes unitários dos serviços</code></pre>

---

## 📬 Testes com Postman
O Postman é utilizado para testar os endpoints da API durante o desenvolvimento. Você pode simular requisições POST, GET, PUT e DELETE, além de validar o envio de e-mails com QR Code e autenticação via token.

Dica: use a aba "Body" no modo JSON para enviar os dados corretamente no cadastro e atualização de funcionários.

---

## 🛢️ Banco de Dados - PostgreSQL e pgAdmin
O banco PostgreSQL armazena os dados de funcionários com segurança e integridade.

Utilizei o pgAdmin para visualizar as tabelas, registros e monitorar o desempenho do banco de forma gráfica.

---
                                
## 📘 Documentação da API — Swagger UI

A aplicação Tag-ID conta com uma documentação interativa da API utilizando o Swagger UI, que permite visualizar todos os endpoints e testar suas requisições diretamente pelo navegador, de forma simples e prática.

---

## 🔗 Acesso ao Swagger

Após iniciar o projeto localmente, acesse o Swagger UI pelo link:

<code><pre>
http://localhost:8080/swagger-ui/index.html
</code></pre>

### 💡 Caso você tenha alterado a porta padrão no arquivo application.yml, substitua 8080 pela porta configurada.

---

## 🧩 Dependência

O Swagger foi integrado ao projeto utilizando a biblioteca Springdoc OpenAPI, já configurada no pom.xml:

<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.3.0</version>
</dependency>

---

## 🔐 Usuário de Teste para o Swagger

Para facilitar os testes de endpoints protegidos (como cadastro, atualização e exclusão), o projeto cria automaticamente um usuário padrão via linha de comando, chamado usuarioTest.

Use as credenciais abaixo para realizar testes no Swagger:

<pre><code>👤 Usuário: usuarioTest
🔑 Senha: 1234</code></pre>


### Esse usuário possui perfil ADMIN, permitindo o acesso completo aos endpoints disponíveis no sistema.

