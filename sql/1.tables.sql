--Drops
drop table if exists log_cooperativa;
drop table if exists log_empresa;
drop table if exists log_leilao;
drop table if exists log_produto;
drop table if exists log_endereco;
drop table if exists log_lance;

drop table if exists lance;
drop table if exists leilao;
drop table if exists produto;
drop table if exists endereco;
drop table if exists empresa;
drop table if exists cooperativa;

--Tabelas de log
create table log_cooperativa(
    log_cooperativa_id serial primary key,
    cod_cooperativa varchar(14),
    data_alteracao date not null,
    operacao varchar(80),
    usuario varchar(80),
    delete_old varchar(14)
);

create table log_empresa(
    log_empresa_id serial primary key,
    cod_empresa varchar(14),
    data_alteracao date not null,
    operacao varchar(80),
    usuario varchar(80),
    delete_old varchar(14)
);

create table log_leilao(
    log_leilao_id serial primary key,
    cod_leilao int,
    data_alteracao date not null,
    operacao varchar(80),
    usuario varchar(80),
    delete_old int
);

create table log_produto(
    log_produto_id serial primary key,
    cod_produto int,
    data_alteracao date not null,
    operacao varchar(80),
    usuario varchar(80),
    delete_old int
);

create table log_endereco(
    log_endereco_id serial primary key,
    cod_endereco int,
    data_alteracao date not null,
    operacao varchar(80),
    usuario varchar(80),
    delete_old int
);

create table log_lance(
    log_lance_id serial primary key,
    cod_lance int,
    data_alteracao date not null,
    operacao varchar(80),
    usuario varchar(80),
    delete_old int
);

--Tabelas normalizadas....................................................
create table cooperativa(
    cnpj_cooperativa VARCHAR(14) primary key,
    nome_cooperativa varchar,
    email_cooperativa varchar,
    senha_cooperativa varchar
);

create table empresa(
    nome_empresa varchar,
    email_empresa varchar,
    senha_empresa varchar,
    telefone_empresa varchar,
    cnpj_empresa varchar(14) primary key
);

create table produto(
    id_produto serial primary key,
    tipo_produto varchar,
    valor_inicial_produto numeric,
    valor_final_produto numeric,
    peso numeric,
    foto_produto varchar
);

create table endereco(
    id_endereco serial primary key,
    cidade varchar,
    rua varchar,
    numero int
);

create table leilao(
    id_leilao serial primary key,
    data_inicio_leilao date,
    data_fim_leilao date,
    detalhes_leilao varchar,
    hora_leilao time,
    id_endereco int REFERENCES endereco(id_endereco),
    id_produto int REFERENCES produto(id_produto)
);

--tabela app
-- preguntar na aula do grilo
CREATE TABLE lance (
    id_lance SERIAL PRIMARY KEY,
    id_leilao INT REFERENCES leilao(id_leilao),
    cnpj_empresa varchar REFERENCES empresa(cnpj_empresa),
    valor NUMERIC,
    data_lance DATE
);