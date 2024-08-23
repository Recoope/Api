
--Função de trigger....................................................
--Cooperativa
create or replace function func_log_cooperativa()
    returns trigger as
$$
declare
    usuario varchar(80);
begin
    select usename from pg_user into usuario;
    insert into log_cooperativa (cod_cooperativa,data_alteracao,operacao,usuario,delete_old) values (new.cnpj_cooperativa,current_date,tg_op,usuario,old.cnpj_cooperativa);
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
    insert into log_empresa (cod_empresa,data_alteracao,operacao,usuario,delete_old) values (new.cnpj_empresa,current_date,tg_op,usuario,old.cnpj_empresa);
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
    insert into log_leilao (cod_leilao,data_alteracao,operacao,usuario,delete_old) values (new.id_leilao,current_date,tg_op,usuario,old.id_leilao);
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
    insert into log_produto (cod_produto,data_alteracao,operacao,usuario,delete_old) values (new.id_produto,current_date,tg_op,usuario,old.id_produto);
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
    insert into log_endereco (cod_endereco,data_alteracao,operacao,usuario,delete_old) values (new.id_endereco,current_date,tg_op,usuario,old.id_endereco);
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
    insert into log_lance (cod_lance,data_alteracao,operacao,usuario,delete_old) values (new.id_lance,current_date,tg_op,usuario,old.id_lance);
    return new;
end;
$$
    language 'plpgsql';

create trigger trg_log_lance
    after insert or update or delete on lance
    for each row
execute procedure func_log_lance();