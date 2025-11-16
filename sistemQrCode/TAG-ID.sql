CREATE TABLE usuario (
    id UUID PRIMARY KEY,
    login VARCHAR(30) NOT NULL,
    senha VARCHAR(300) NOT NULL,
    roles VARCHAR[]
);


CREATE TABLE cargo (
    id SERIAL PRIMARY KEY,
    nome_cargo VARCHAR(35) NOT NULL
);


CREATE TABLE setor (
    id SERIAL PRIMARY KEY,
    nome_setor VARCHAR(35) NOT NULL
);


CREATE TABLE funcionarios (
    id SERIAL PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    matricula UUID NOT NULL UNIQUE,
    email_corporativo VARCHAR(100) NOT NULL UNIQUE,

    id_cargo INTEGER,
    id_setor INTEGER,
    id_usuario UUID,

    data_cadastro TIMESTAMP,
    data_atualizacao TIMESTAMP,

    CONSTRAINT fk_func_cargo FOREIGN KEY (id_cargo)
        REFERENCES cargo(id) ON DELETE SET NULL,

    CONSTRAINT fk_func_setor FOREIGN KEY (id_setor)
        REFERENCES setor(id) ON DELETE SET NULL,

    CONSTRAINT fk_func_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id) ON DELETE SET NULL
);


CREATE TABLE registro_acesso (
    id SERIAL PRIMARY KEY,

    funcionario_id INTEGER,
    data_hora_acesso TIMESTAMP,
    status VARCHAR(255),

    setor_id INTEGER,
    cargo_id INTEGER,

    CONSTRAINT fk_reg_funcionario FOREIGN KEY (funcionario_id)
        REFERENCES funcionarios(id) ON DELETE CASCADE,

    CONSTRAINT fk_reg_setor FOREIGN KEY (setor_id)
        REFERENCES setor(id) ON DELETE SET NULL,

    CONSTRAINT fk_reg_cargo FOREIGN KEY (cargo_id)
        REFERENCES cargo(id) ON DELETE SET NULL
);


CREATE TABLE veiculo (
    id SERIAL PRIMARY KEY,

    modelo_carro VARCHAR(30) NOT NULL,
    ano_modelo INTEGER NOT NULL,
    placa VARCHAR(10) NOT NULL,

    funcionario_id INTEGER,
    usuario_id UUID,

    data_cadastro TIMESTAMP,
    data_atualizacao TIMESTAMP,

    CONSTRAINT fk_veic_func FOREIGN KEY (funcionario_id)
        REFERENCES funcionarios(id) ON DELETE SET NULL,

    CONSTRAINT fk_veic_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuario(id) ON DELETE SET NULL
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

insert into usuario
    (id, login, senha, roles)
    values
    (uuid_generate_v4(),
    'gerente',
    (senha_do_usuario_aqui),
    '{ADMIN}');