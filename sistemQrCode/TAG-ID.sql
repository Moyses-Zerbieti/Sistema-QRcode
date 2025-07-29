    create table funcionarios(
    id serial primary key,
    nome_completo varchar(100)not null,
    cpf varchar(14)not null unique,
    data_nascimento date not null,
    matricula uuid not null unique,
    email_corporativo varchar(100) not null unique,
    cargo varchar(20) not null,
    setor varchar(30) not null
    );

    create table usuario(
    id uuid not null primary key,
    login varchar(30) not null unique,
    senha varchar(300) not null,
    roles varchar[]
    );