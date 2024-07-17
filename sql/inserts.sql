insert into cooperativa (id_cooperativa, nome_cooperativa, email_cooperativa, senha_cooperativa)
values
    (1, 'Cooperativa Viva', 'coopviva@email.com', 'senha123'),
    (2, 'SuperCoop', 'supercoop@email.com', 'senha456'),
    (3, '365 Cooperativa', '365coop@email.com', 'senha789'),
    (4, 'Cooperativa 3 Irmãos', 'cooperativa3irmaos@email.com', 'senhaabc'),
    (5, 'Green Cooperativa', 'gcoop@email.com', 'senhaxyz');

insert into empresa (id_empresa, nome_empresa, email_empresa, senha_empresa, telefone_empresa, cnpj_empresa)
values
    (1, 'Switf', 'ouvidoria@switfembalagens.com', 'senha123', '(11) 98765-4321', '12345678901234'),
    (2, 'Freeboy Inc.', 'atendimento@freeboy.com', 'senha456', '(21) 98765-4321', '56789012345678'),
    (3, 'JSA', 'atendimento@jsa.com', 'senha789', '(31) 98765-4321', '90123456781234'),
    (4, 'Picpacks', 'sac@picpacks.com', 'senhaabc', '(41) 98765-4321', '34567890123456');

insert into endereco (id_endereco, cidade, rua, numero)
values
    (1, 'São Paulo', 'Av. Paulista', 1000),
    (2, 'São Paulo', 'Av. Giovanni Gronchi', 500),
    (3, 'Taboão da Serra', 'Estrada das Olarias', 200),
    (4, 'São Bernardo do Campo', 'Av. do Taboão', 300);

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
    (5, '2024-08-05', '2024-08-11', 'Leilão de resíduos orgânicos', '13:00', 1, 5, 5);

insert into lance (id_lance, id_leilao, id_empresa, valor, data_lance)
values
    (1, 1, 1, 55.00, '2024-08-02'),
    (2, 1, 2, 60.00, '2024-08-03'),
    (3, 2, 3, 35.00, '2024-08-04'),
    (4, 2, 4, 40.00, '2024-08-05'),
    (5, 3, 5, 25.00, '2024-08-06'),
    (6, 3, 1, 30.00, '2024-08-07'),
    (7, 4, 2, 45.00, '2024-08-08'),
    (8, 4, 3, 50.00, '2024-08-09'),
    (9, 5, 4, 20.00, '2024-08-10'),
    (10, 5, 5, 22.00, '2024-08-11');
