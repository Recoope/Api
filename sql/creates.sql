
drop table if exists lance;
drop table if exists leilao;
drop table if exists cooperativa;
drop table if exists empresa;
drop table if exists endereco;
drop table if exists produto;

create table endereco(
    id_endereco serial primary key,
    cidade varchar(100),
    bairro varchar(100),
    rua varchar(100),
    numero int
);

create table cooperativa(
    id_cooperativa serial primary key,
    nome_cooperativa varchar(100),
    email_cooperativa varchar(100),
    senha_cooperativa varchar(100),
    registro_cooperativa date
);

create table empresa(
    id_empresa serial primary key,
    nome_empresa varchar(100),
    email_empresa varchar(100),
    senha_empresa varchar(100),
    telefone_empresa varchar(15),
    cnpj_empresa varchar(20),
    registro_empresa date
);

create table produto(
    id_produto serial primary key,
    tipo_produto varchar(100),
    valor_produto numeric(12, 2),
    peso numeric(8, 2),
    foto_leilao varchar(1000)
);

create table leilao (
    id_leilao serial primary key,
    data_inicio_leilao date,
    data_fim_leilao date,
    detalhes_leilao varchar(1000),
    hora_leilao time,
    id_endereco int references endereco(id_endereco),
    id_produto int references produto(id_produto),
    id_cooperativa int references cooperativa(id_cooperativa)
);

create table lance(
    id_lance serial primary key,
    id_leilao int references leilao(id_leilao),
    id_empresa int references empresa(id_empresa),
    valor numeric(12, 2),
    data_lance date
);