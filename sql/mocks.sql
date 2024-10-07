-- Inserindo dados na tabela cooperativa
INSERT INTO cooperativa (cnpj_cooperativa, nome_cooperativa, email_cooperativa, senha_cooperativa) VALUES
('07078284000188', 'Cooperativa A', 'coopA@email.com', 'senha123'),
('23456789000111', 'Cooperativa B', 'coopB@email.com', 'senha456');

-- Inserindo dados na tabela empresa
INSERT INTO empresa (cnpj_empresa, nome_empresa, email_empresa, senha_empresa, telefone_empresa) VALUES
('98765432000155', 'Empresa X', 'empresaX@email.com', 'pass123', '11987654321'),
('87654321000144', 'Empresa Y', 'empresaY@email.com', 'pass456', '11876543210');

-- Inserindo dados na tabela endereco
INSERT INTO endereco (id_endereco, cidade, rua, numero) VALUES
(1, 'São Paulo', 'Rua A', 100),
(2, 'Rio de Janeiro', 'Rua B', 200);

-- Inserindo dados na tabela leilao
INSERT INTO leilao (id_leilao, data_inicio_leilao, data_fim_leilao, detalhes_leilao, hora_leilao, leilao_fim, id_endereco, cnpj_cooperativa) VALUES
(1, '2024-01-01', '2024-01-10', 'Leilão de materiais recicláveis A', '10:00:00', 'N', 1, '07078284000188'),
(2, '2024-02-15', '2024-02-20', 'Leilão de materiais recicláveis B', '14:00:00', 'S', 2, '62365285000111'),
(3, '2024-10-01', '2024-10-07', 'Leilão de materiais recicláveis C', '09:00:00', 'N', 1, '07078284000188'),
(4, '2024-10-05', '2024-10-10', 'Leilão de materiais recicláveis D', '11:00:00', 'N', 2, '62365285000111'),
(5, '2024-10-08', '2024-10-15', 'Leilão de materiais recicláveis E', '13:00:00', 'N', 1, '07078284000188'),
(6, '2024-10-10', '2024-10-12', 'Leilão de materiais recicláveis F', '15:00:00', 'N', 2, '62365285000111'),
(7, '2024-11-01', '2024-11-05', 'Leilão de materiais recicláveis G', '10:00:00', 'N', 1, '07078284000188');

-- Inserindo dados na tabela produto
INSERT INTO produto (id_produto, tipo_produto, valor_inicial_produto, valor_final_produto, peso_produto, foto_produto, id_leilao) VALUES
(1, 'METAL', 100.00, 150.00, 50.0, 'foto1.jpg', 1),
(2, 'PLASTICO', 200.00, 250.00, 30.0, 'foto2.jpg', 1),
(3, 'VIDRO', 50.00, 75.00, 20.0, 'foto3.jpg', 2),
(4, 'PAPEL', 30.00, 40.00, 10.0, 'foto4.jpg', 2);

-- Inserindo dados na tabela lance
INSERT INTO lance (id_lance, id_leilao, cnpj_empresa, valor, data_lance) VALUES
(1, 1, '18347306000184', 120.00, '2024-01-02'),
(2, 1, '11553558000110', 130.00, '2024-01-03'),
(3, 2, '18347306000184', 55.00, '2024-02-16'),
(4, 2, '11553558000110', 60.00, '2024-02-17'),
(5, 3, '18347306000184', 110.00, '2024-10-02'),
(6, 3, '11553558000110', 120.00, '2024-10-04'),
(7, 4, '18347306000184', 230.00, '2024-10-06'),
(8, 4, '11553558000110', 240.00, '2024-10-07'),
(9, 5, '18347306000184', 140.00, '2024-10-09'),
(10, 6, '11553558000110', 220.00, '2024-10-10'),
(11, 7, '18347306000184', 150.00, '2024-11-02'),
(12, 7, '11553558000110', 160.00, '2024-11-03');;
