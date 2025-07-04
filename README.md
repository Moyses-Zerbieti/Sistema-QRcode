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

✅ Cadastro de funcionários (nome completo, CPF, e-mail corporativo, data de nascimento, cargo e setor de trabalho).

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
                  ├── controller/  #Endpoints REST
                  ├── dto/         #Transferência de dados
                  ├── model/       # Entidades do sistema
                  ├── repository/  # Interfaces JPA
                  └── service/     # Regras de negócio</code></pre>

---

## 📬 Testes com Postman
O Postman é utilizado para testar os endpoints da API durante o desenvolvimento. Você pode simular requisições POST, GET, PUT e DELETE, além de validar o envio de e-mails com QR Code e autenticação via token.

Dica: use a aba "Body" no modo JSON para enviar os dados corretamente no cadastro e atualização de funcionários.

---

## 🛢️ Banco de Dados - PostgreSQL e pgAdmin
O banco PostgreSQL armazena os dados de funcionários com segurança e integridade.

Utilizei o pgAdmin para visualizar as tabelas, registros e monitorar o desempenho do banco de forma gráfica.

---

## 📌 Próximos Passos
 ## FEATURE:
- Criar endpoint PATCH para atualização parcial dos dados de funcionários (permitindo alterar apenas campos editáveis como e-mail corporativo, cargo e setor, por exemplo).
- Registrar o tempo restante para novo acesso na resposta.
- Enviar e-mail ou notificação em tentativas repetidas.
                


