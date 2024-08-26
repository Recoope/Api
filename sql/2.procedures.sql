--Procedure para verificar se tudo que foi para o bancoi esta certo........................................
--Cooperativa
create or replace procedure insert_cooperativa (c_cnpj varchar, c_nome varchar, c_email varchar, c_senha varchar, c_registro varchar)
    language 'plpgsql' as
$$
begin
    if c_email SIMILAR TO '%[@.]%' then
        if length(c_senha)>7 and c_senha SIMILAR TO '%[0-9]%' and c_senha SIMILAR TO '%[@.*%#!]%' then
            if c_email SIMILAR TO '%[@.]%' then
                INSERT INTO cooperativa (cnpj_cooperativa, nome_cooperativa, email_cooperativa, senha_cooperativa, registro_cooperativa) VALUES (c_cnpj, c_nome, c_email, c_senha, current_date);
            else raise exception 'CNPJ deve ter 14 digitos!!!';
            end if;
        else raise exception 'Senha menor que 8 dígitos ou não tem numeros ou não possui caracteres especiais!!!';
        end if;
    else raise exception 'Email deve conter @ e .!!!';
    end if;
commit;
end;
$$;

--Empresa
create or replace procedure insert_empresa (e_cnpj varchar, e_nome varchar, e_email varchar, e_senha varchar, e_telefone varchar)
    language 'plpgsql' as
$$
begin
    if e_email SIMILAR TO '%[@.]%' then
        if length(e_senha)>7 and e_senha SIMILAR TO '%[0-9]%' and e_senha SIMILAR TO '%[@.*%#!]%' then
            if length(e_telefone)=11 and e_telefone SIMILAR TO '[0-9]+' then
                if length(e_cnpj)=14 then
                    INSERT INTO empresa (cnpj_empresa, nome_empresa, email_empresa, senha_empresa, telefone_empresa, registro_empresa) VALUES (e_cnpj, e_nome, e_email, e_senha, e_telefone, current_date);
                else raise exception 'CNPJ não tem 14 digitos!!!';
                end if;
            else raise exception 'Telefone não tem 11 dígitos ou tem letras!!!';
            end if;
        else raise exception 'Senha menor que 8 dígitos ou não tem numeros ou não possui caracteres especiais!!!';
        end if;
    else raise exception 'Email não pode conter números e deve ter (@ e .)!!!';
    end if;
commit;
end;
$$;

-- Lance
create or replace procedure insert_lance (id_leilao int, cnpj_empresa date, l_detalhes varchar, l_hora time, l_id_endereco int, l_id_produto int)
    language 'plpgsql' as
$$
begin
    if l_data_inicio < l_data_fim then
        if exists (select id_endereco from endereco where id_endereco=l_id_endereco) and exists (select id_produto from produto where id_produto=l_id_produto) then
            INSERT INTO leilao (data_inicio_leilao, data_fim_leilao, detalhes_leilao, hora_leilao) VALUES (l_data_inicio, l_data_fim, l_detalhes, l_hora);
        else raise exception 'Esse endereço ou produto não existe!!!';
        end if;
    else raise exception 'Data de início menor do que a de fim!!!';
    end if;
commit;
end;
$$;

--Endereco
create or replace procedure insert_endereco (en_cidade varchar, en_rua varchar, en_numero int)
    language 'plpgsql' as
$$
begin
    if not en_cidade SIMILAR TO '%[0-9]%' then
        INSERT INTO endereco (cidade, rua, numero) VALUES (en_cidade, en_rua, en_numero);
    else raise exception 'Cidade não pode conter número!!!';
    end if;
commit;
end;
$$;

--Leilão
create or replace procedure insert_leilao (l_data_inicio date, l_data_fim date, l_detalhes varchar, l_hora time, l_id_endereco int, l_id_produto int)
    language 'plpgsql' as
$$
begin
    if l_data_inicio < l_data_fim then
        if exists (select id_endereco from endereco where id_endereco=l_id_endereco) and exists (select id_produto from produto where id_produto=l_id_produto) then
            INSERT INTO leilao (data_inicio_leilao, data_fim_leilao, detalhes_leilao, hora_leilao) VALUES (l_data_inicio, l_data_fim, l_detalhes, l_hora);
else raise exception 'Esse endereço ou produto não existe!!!';
end if;
else raise exception 'Data de início menor do que a de fim!!!';
end if;
commit;
end;
$$;

--Endereco
create or replace procedure insert_endereco (en_cidade varchar, en_rua varchar, en_numero int)
    language 'plpgsql' as
$$
begin
    if not en_cidade SIMILAR TO '%[0-9]%' then
        INSERT INTO endereco (cidade, rua, numero) VALUES (en_cidade, en_rua, en_numero);
else raise exception 'Cidade não pode conter número!!!';
end if;
commit;
end;
$$;

