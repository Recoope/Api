-- fazer uma procedure que pegue da tabela de lance o maior valor de um leilão e atualize
select * from cooperativa;
select * from empresa;
select * from produto;
select * from endereco;
select * from leilao;
select * from lance;


select * from log_cooperativa;
select * from log_empresa;
select * from log_produto;
select * from log_endereco;
select * from log_leilao;
select * from log_lance;

--Tabelas de log
create table log_cooperativa(
                                cod int,
                                data_alteracao date not null,
                                operacao varchar(80),
                                usuario varchar(80),
                                delete_old int
);

create table log_empresa(
                            cod int,
                            data_alteracao date not null,
                            operacao varchar(80),
                            usuario varchar(80),
                            delete_old int
);

create table log_leilao(
                           cod int,
                           data_alteracao date not null,
                           operacao varchar(80),
                           usuario varchar(80),
                           delete_old int
);

create table log_produto(
                            cod int,
                            data_alteracao date not null,
                            operacao varchar(80),
                            usuario varchar(80),
                            delete_old int
);

create table log_endereco(
                             cod int,
                             data_alteracao date not null,
                             operacao varchar(80),
                             usuario varchar(80),
                             delete_old int
);

create table log_lance(
                          cod int,
                          data_alteracao date not null,
                          operacao varchar(80),
                          usuario varchar(80),
                          delete_old int
);

-- Tabelas normalizadas....................................................
create table cooperativa(
                            id_cooperativa serial primary key,
                            nome_cooperativa varchar,
                            email_cooperativa varchar,
                            senha_cooperativa varchar
);

create table empresa(
                        nome_empresa varchar,
                        email_empresa varchar,
                        senha_empresa varchar,
                        telefone_empresa varchar,
                        cnpj_empresa varchar primary key
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

--Procedure para verificar se tudo que foi para o bancoi esta certo........................................
--Cooperativa
create or replace procedure insert_cooperativa (c_nome varchar, c_email varchar, c_senha varchar)
language 'plpgsql' as
$$
begin
    if not c_nome SIMILAR TO '%[0-9]%' then
		if c_email SIMILAR TO '%[@.]%' and not c_email SIMILAR TO '%[0-9]%' then
			if length(c_senha)>7 and c_senha SIMILAR TO '%[0-9]%' and c_senha SIMILAR TO '%[@.*%#!]%' then
				INSERT INTO cooperativa (nome_cooperativa, email_cooperativa, senha_cooperativa) VALUES (c_nome, c_email, c_senha);

else
				raise exception 'Senha menor que 8 dígitos ou não tem numeros ou não possui caracteres especiais!!!';
end if;
else
			raise exception 'Email não pode conter números e deve ter (@ e .)!!!';
end if;
else
		raise exception 'Nome não pode conter número!!!';
end if;

commit;
end;
$$;

call insert_cooperativa('nome','nome@gmail.com','senha123!');

--Empresa
create or replace procedure insert_empresa (e_nome varchar, e_email varchar, e_senha varchar, e_telefone varchar, e_cnpj varchar)
language 'plpgsql' as
$$
begin
    if not e_nome SIMILAR TO '%[0-9]%' then
		if e_email SIMILAR TO '%[@.]%' and not e_email SIMILAR TO '%[0-9]%' then
			if length(e_senha)>7 and e_senha SIMILAR TO '%[0-9]%' and e_senha SIMILAR TO '%[@.*%#!]%' then
				if length(e_telefone)=11 and e_telefone SIMILAR TO '[0-9]+' then
					if e_cnpj SIMILAR TO '[0-9]{2}\.[0-9]{3}\.[0-9]{3}/0001-[0-9]{2}' then
						INSERT INTO empresa (nome_empresa, email_empresa, senha_empresa, telefone_empresa, cnpj_empresa) VALUES (e_nome, e_email, e_senha, e_telefone, e_cnpj);

else
						raise exception 'CNPJ não tem 19 dígitos ou tem letras!!!';
end if;
else
					raise exception 'telefone não tem 11 dígitos ou tem letras!!!';
end if;

else
				raise exception 'Senha menor que 8 dígitos ou não tem numeros ou não possui caracteres especiais!!!';
end if;
else
			raise exception 'Email não pode conter números e deve ter (@ e .)!!!';
end if;
else
		raise exception 'Nome não pode conter número!!!';
end if;

commit;
end;
$$;

call insert_empresa('nome','nome@gmail.com','senha123!','11973733007','12.345.678/0001-00');

--Leilão
create or replace procedure insert_leilao (l_data_inicio date, l_data_fim date, l_detalhes varchar, l_hora time, l_id_endereco int, l_id_produto int)
language 'plpgsql' as
$$
begin

   	if l_data_inicio < l_data_fim then
		if exists (select id_endereco from endereco where id_endereco=l_id_endereco) and exists (select id_produto from produto where id_produto=l_id_produto) then
			INSERT INTO leilao (data_inicio_leilao, data_fim_leilao, detalhes_leilao, hora_leilao) VALUES (l_data_inicio, l_data_fim, l_detalhes, l_hora);
else
   			raise exception 'Esse endereço ou produto não existe!!!';
end if;
else
   		raise exception 'Data de início menor do que a de fim!!!';
end if;

commit;
end;
$$;

call insert_leilao('2000-7-20','2001-7-20','nebvhrbvhbvhjrbjvbjhr','12:05:20',1,1);

--Endereco
create or replace procedure insert_endereco (en_cidade varchar, en_rua varchar, en_numero int)
language 'plpgsql' as
$$
begin
    if not en_cidade SIMILAR TO '%[0-9]%' then
		INSERT INTO endereco (cidade, rua, numero) VALUES (en_cidade, en_rua, en_numero);
else
		raise exception 'Cidade não pode conter número!!!';
end if;

commit;
end;
$$;

call insert_endereco('São paulo','Rua marechal hermes da fonesca',10);

--Função de trigger....................................................
--Cooperativa
create or replace function func_log_cooperativa()
returns trigger as
$$
declare
usuario varchar(80);
begin
select usename from pg_user into usuario;
insert into log_cooperativa (cod,data_alteracao,operacao,usuario,delete_old) values (new.id_cooperativa,current_date,tg_op,usuario,old.id_cooperativa);
return new;
end;
$$
language 'plpgsql';

create trigger trg_log_cooperativa
    after insert or update or delete on cooperativa
    for each row
    execute procedure func_log_cooperativa();

--Empresa
create or replace function func_log_empresa()
returns trigger as
$$
declare
usuario varchar(80);
begin
select usename from pg_user into usuario;
insert into log_empresa (cod,data_alteracao,operacao,usuario,delete_old) values (new.cnpj_empresa,current_date,tg_op,usuario,old.cnpj_empresa);
return new;
end;
$$
language 'plpgsql';

create trigger trg_log_empresa
    after insert or update or delete on empresa
    for each row
    execute procedure func_log_empresa();

--Leilao
create or replace function func_log_leilao()
returns trigger as
$$
declare
usuario varchar(80);
begin
select usename from pg_user into usuario;
insert into log_leilao (cod,data_alteracao,operacao,usuario,delete_old) values (new.id_leilao,current_date,tg_op,usuario,old.id_leilao);
return new;
end;
$$
language 'plpgsql';

create trigger trg_log_leilao
    after insert or update or delete on leilao
    for each row
    execute procedure func_log_leilao();

--Produto
create or replace function func_log_produto()
returns trigger as
$$
declare
usuario varchar(80);
begin
select usename from pg_user into usuario;
insert into log_produto (cod,data_alteracao,operacao,usuario,delete_old) values (new.id_produto,current_date,tg_op,usuario,old.id_produto);
return new;
end;
$$
language 'plpgsql';

create trigger trg_log_produto
    after insert or update or delete on produto
    for each row
    execute procedure func_log_produto();

--Endereco
create or replace function func_log_endereco()
returns trigger as
$$
declare
usuario varchar(80);
begin
select usename from pg_user into usuario;
insert into log_endereco (cod,data_alteracao,operacao,usuario,delete_old) values (new.id_endereco,current_date,tg_op,usuario,old.id_endereco);
return new;
end;
$$
language 'plpgsql';

create trigger trg_log_endereco
    after insert or update or delete on endereco
    for each row
    execute procedure func_log_endereco();

--Lance
create or replace function func_log_lance()
returns trigger as
$$
declare
usuario varchar(80);
begin
select usename from pg_user into usuario;
insert into log_lance (cod,data_alteracao,operacao,usuario,delete_old) values (new.id_lance,current_date,tg_op,usuario,old.id_lance);
return new;
end;
$$
language 'plpgsql';

create trigger trg_log_lance
    after insert or update or delete on lance
    for each row
    execute procedure func_log_lance();