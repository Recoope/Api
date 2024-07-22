
insert into endereco (id_endereco, cidade, bairro, rua, numero)
values
    (1, 'São Paulo', 'Bela Vista', 'Av. Paulista', 1000),
    (2, 'São Paulo', 'Morumbi', 'Av. Giovanni Gronchi', 500),
    (3, 'Taboão da Serra', 'Jardim Triangulo', 'Estrada das Olarias', 200),
    (4, 'São Bernardo do Campo', 'Taboão', 'Av. do Taboão', 300),
    (5, 'São Paulo', 'Pinheiros', 'Rua dos Pinheiros', 200),
    (6, 'São Paulo', 'Vila Madalena', 'Rua Harmonia', 500),
    (7, 'São Paulo', 'Itaim Bibi', 'Av. Brigadeiro Faria Lima', 1500),
    (8, 'São Paulo', 'Moema', 'Av. Ibirapuera', 800),
    (9, 'São Paulo', 'Vila Mariana', 'Rua Vergueiro', 1000);

insert into cooperativa (id_cooperativa, nome_cooperativa, email_cooperativa, senha_cooperativa, registro_cooperativa, id_endereco)
values
    (1, 'Cooperativa Viva', 'coopviva@email.com', 'senha123', '2019-08-28'),
    (2, 'SuperCoop', 'supercoop@email.com', 'senha456', '2020-07-18'),
    (3, '365 Cooperativa', '365coop@email.com', 'senha789', '2022-01-22'),
    (4, 'Cooperativa 3 Irmãos', 'cooperativa3irmaos@email.com', 'senhaabc', '2023-12-08'),
    (5, 'Green Cooperativa', 'gcoop@email.com', 'senhaxyz', '2024-03-19');

insert into empresa (id_empresa, nome_empresa, email_empresa, senha_empresa, telefone_empresa, cnpj_empresa, registro_empresa)
values
    (1, 'Switf', 'ouvidoria@switfembalagens.com', 'senha123', '(11) 98765-4321', '12345678901234', '2017-12-21'),
    (2, 'Freeboy Inc.', 'atendimento@freeboy.com', 'senha456', '(21) 98765-4321', '56789012345678', '2019-09-10'),
    (3, 'JSA', 'atendimento@jsa.com', 'senha789', '(31) 98765-4321', '90123456781234', '2022-04-29'),
    (4, 'Picpacks', 'sac@picpacks.com', 'senhaabc', '(41) 98765-4321', '34567890123456', '2023-10-06');

insert into produto (id_produto, tipo_produto, valor_produto, peso, foto_leilao)
values
    (1, 'Plástico', 50.00, 10.5, 'https://cempre.org.br/wp-content/uploads/2020/11/titimg-rec-platico.png'),
    (2, 'Papel', 30.00, 8.2, 'https://assets-global.website-files.com/5afd737ac94d739e059655ac/5b367924f23df374f96da767_size_960_16_9_bolinhadepapel.png'),
    (3, 'Vidro', 20.00, 15.0, 'https://expresso.estadao.com.br/sao-paulo/wp-content/uploads/2023/08/IMG_3237.jpg'),
    (4, 'Metal', 40.00, 12.8, 'https://www.5mmetais.com.br/blog/wp-content/uploads/2022/01/reciclagem-de-metal.jpg'),
    (5, 'Orgânico', 15.00, 5.5, 'https://aparasliberdade.com.br/wp-content/uploads/2024/05/lixo-domestico-saiba-com-mais-detalhes-como-reaproveita-lo.jpg');

insert into leilao (id_leilao, data_inicio_leilao, data_fim_leilao, detalhes_leilao, hora_leilao, id_endereco, id_produto, id_cooperativa)
values
    (1, '2024-08-01', '2024-08-07', 'Leilão de plásticos recicláveis', '15:00', 1, 1, 1),
    (2, '2024-08-02', '2024-08-08', 'Leilão de papéis recicláveis', '14:00', 2, 2, 2),
    (3, '2024-08-03', '2024-08-09', 'Leilão de vidros recicláveis', '16:00', 3, 3, 3),
    (4, '2024-08-04', '2024-08-10', 'Leilão de metais recicláveis', '17:00', 4, 4, 4),
    (5, '2024-08-05', '2024-08-11', 'Leilão de resíduos orgânicos', '13:00', 1, 5, 5),
    (6, '2024-08-06', '2024-08-12', 'Leilão de eletrônicos recicláveis', '15:00', 2, 1, 2),
    (7, '2024-08-07', '2024-08-13', 'Leilão de móveis recicláveis', '14:00', 3, 2, 3),
    (8, '2024-08-08', '2024-08-14', 'Leilão de roupas recicláveis', '16:00', 4, 3, 4),
    (9, '2024-08-09', '2024-08-15', 'Leilão de brinquedos recicláveis', '17:00', 1, 4, 5),
    (10, '2024-08-10', '2024-08-16', 'Leilão de livros recicláveis', '13:00', 2, 5, 1),
    (11, '2024-08-11', '2024-08-17', 'Leilão de utensílios de cozinha recicláveis', '15:00', 3, 1, 2),
    (12, '2024-08-12', '2024-08-18', 'Leilão de materiais de construção recicláveis', '14:00', 4, 2, 3),
    (13, '2024-08-13', '2024-08-19', 'Leilão de baterias recicláveis', '16:00', 1, 3, 4),
    (14, '2024-08-14', '2024-08-20', 'Leilão de pneus recicláveis', '17:00', 2, 4, 5),
    (15, '2024-08-15', '2024-08-21', 'Leilão de materiais de escritório recicláveis', '13:00', 3, 5, 1),
    (16, '2024-08-16', '2024-08-22', 'Leilão de instrumentos musicais recicláveis', '15:00', 4, 1, 2),
    (17, '2024-08-17', '2024-08-23', 'Leilão de máquinas e ferramentas recicláveis', '14:00', 1, 2, 3),
    (18, '2024-08-18', '2024-08-24', 'Leilão de equipamentos de jardinagem recicláveis', '16:00', 2, 3, 4),
    (19, '2024-08-19', '2024-08-25', 'Leilão de produtos de limpeza recicláveis', '17:00', 3, 4, 5),
    (20, '2024-08-20', '2024-08-26', 'Leilão de artigos esportivos recicláveis', '13:00', 4, 5, 1),
    (21, '2024-08-21', '2024-08-27', 'Leilão de acessórios de moda recicláveis', '15:00', 1, 1, 2),
    (22, '2024-08-22', '2024-08-28', 'Leilão de embalagens recicláveis', '14:00', 2, 2, 3);

insert into lance (id_lance, id_leilao, id_empresa, valor, data_lance)
values
    (1, 1, 1, 55.00, '2024-08-02'),
    (2, 1, 2, 60.00, '2024-08-03'),
    (3, 2, 3, 35.00, '2024-08-04'),
    (4, 2, 4, 40.00, '2024-08-05'),
    (5, 3, 3, 25.00, '2024-08-06'),
    (6, 3, 1, 30.00, '2024-08-07'),
    (7, 4, 2, 45.00, '2024-08-08'),
    (8, 4, 3, 50.00, '2024-08-09'),
    (9, 5, 4, 20.00, '2024-08-10'),
    (10, 5, 4, 22.00, '2024-08-11');