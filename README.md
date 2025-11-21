# Tag-ID

## Sistema de Cadastro de Funcionários com QR Code

[![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)](https://github.com/seuusuario/seurepositorio)  
[![Java](https://img.shields.io/badge/Java-21+-blue)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)  
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-green)](https://spring.io/projects/spring-boot)  

---

## Descrição

### Este sistema foi desenvolvido com Java 21 e Spring Boot 3.4.5, e tem como objetivo facilitar a gestão de funcionários de uma empresa, desde o cadastro até o envio automático de um QR Code para identificação, via e-mail corporativo. Ele conta com uma API REST documentada com Swagger e testada com Postman, além de persistência de dados usando PostgreSQL e gerenciamento visual com pgAdmin.

---

## 💻 Tecnologias Utilizadas
```
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

Docker (containerização da aplicação e do banco de dados
```

---

## 📂 Organização das Pastas

<pre><code>SistemQrCode/
  ├── .env                    # Variáveis de ambiente para configuração do projeto
  ├── .env-template           # Modelo de variáveis de ambiente
  ├── Dockerfile              # Dockerfile para containerizar a aplicação
  ├── entrypoint.sh           # Script de inicialização da aplicação no Docker
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
         └─ test/
            └─ java/
               └─ com/codigoQR/sistemQrCode/
                 └─ serviceTest/        # Testes unitários dos serviços</code></pre>

---

## ⚙️ Configuração das Variáveis de Ambiente

Antes de iniciar a aplicação, é necessário configurar as variáveis de ambiente que o sistema utiliza para conexão com o banco de dados e envio de e-mails. Para isso, crie ou edite o arquivo .env na raiz do projeto (você pode usar o .env-template como base).


### 🔹 Variáveis do Banco de Dados (PostgreSQL)
| Variável              | Descrição                                                                                 |
| --------------------- | ----------------------------------------------------------------------------------------- |
| `POSTGRES_DB`         | Nome do banco de dados que será utilizado pelo Tag-ID. Ex: `meu_banco`                       |
| `POSTGRES_USER`       | Usuário do banco de dados. Ex: `meu_usuario`                                           |
| `POSTGRES_PASSWORD`   | Senha do usuário do banco de dados. Ex: `minha_senha`                                  |
| `DATASOURCE_URL`      | URL de conexão JDBC do PostgreSQL. Ex: `jdbc:postgresql://NOME_DO_CONTEINER_DO_BANCO_DE_DADOS:5432/meu_banco`   |
| `DATASOURCE_USERNAME` | Usuário do banco de dados usado pela aplicação (geralmente o mesmo de POSTGRES_USER)      |
| `DATASOURCE_PASSWORD` | Senha usada pela aplicação para acessar o banco (geralmente o mesmo de POSTGRES_PASSWORD) |

### 💡 Dica: se estiver usando Docker, configure POSTGRES_DB, POSTGRES_USER e POSTGRES_PASSWORD de acordo com o que foi definido nos containers.

---

| Variável         | Descrição                                                                                     |
| ---------------- | --------------------------------------------------------------------------------------------- |
| `EMAIL_USER`     | E-mail que será utilizado para enviar os QR Codes aos funcionários. Ex: `seu-email@gmail.com` |
| `EMAIL_PASSWORD` | Senha de aplicativo gerada no Google para envio de e-mails. **Não use sua senha normal**.     |

### Como gerar a senha de aplicativo no Gmail:

Acesse Conta Google > Segurança > Senhas de App
.

Escolha Selecionar app > Outro (Personalizado) e dê um nome (ex: TagID).

Clique em Gerar e copie a senha gerada.

Cole essa senha no arquivo .env em EMAIL_PASSWORD.

🔐 Essa senha permite que a aplicação envie e-mails em seu nome sem expor sua senha real.

### 🔹 Exemplo de `.env` configurado

```env
POSTGRES_DB=meu_banco
POSTGRES_USER=meu_usuario
POSTGRES_PASSWORD=minha_senha

DATASOURCE_URL=jdbc:postgresql://NOME_DO_CONTEINER_DO_BANCO_DE_DADOS:5432/meu_banco
DATASOURCE_USERNAME=meu_usuario
DATASOURCE_PASSWORD=minha_senha

EMAIL_USER=seu-email@gmail.com
EMAIL_PASSWORD=senha-gerada-pelo-google
```
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

<pre><code>👤 Usuário: adminTest
🔑 Senha: 1234</code></pre>


### Esse usuário possui perfil ADMIN, permitindo o acesso completo aos endpoints disponíveis no sistema.
