INSERT INTO cooperativa (cnpj_cooperativa, nome_cooperativa, email_cooperativa, senha_cooperativa, registro_cooperativa) VALUES
('12345678000101', 'Cooperativa Alfa', 'contato@coopalpha.com', 'senha123', '2024-03-02'),
('98765432000123', 'Cooperativa Beta', 'contato@coopbeta.com', 'senha456', '2024-02-02');

INSERT INTO empresa (nome_empresa, email_empresa, senha_empresa, telefone_empresa, cnpj_empresa, registro_empresa) VALUES
('Empresa XYZ', 'contato@xyz.com', 'empresaxyz123', '11987654321', '11122233000144', '2024-03-02'),
('Empresa ABC', 'contato@abc.com', 'empresaabc123', '21987654321', '22233344000155', '2024-02-02');

INSERT INTO produto (tipo_produto, valor_inicial_produto, valor_final_produto, peso, foto_produto) VALUES
('Café', 1000.00, 1500.00, 1000.5, 'foto_cafe.jpg'),
('Soja', 2000.00, 2500.00, 2000.0, 'foto_soja.jpg');

INSERT INTO endereco (cidade, rua, numero) VALUES
('São Paulo', 'Rua A', 100),
('Rio de Janeiro', 'Rua B', 200);

INSERT INTO leilao (data_inicio_leilao, data_fim_leilao, detalhes_leilao, hora_leilao, id_endereco, id_produto) VALUES
('2024-09-01', '2024-09-02', 'Leilão de Café', '10:00:00', 1, 1),
('2024-10-01', '2024-10-02', 'Leilão de Soja', '14:00:00', 2, 2);

INSERT INTO lance (id_leilao, cnpj_empresa, valor, data_lance) VALUES
(1, '11122233000144', 1200.00, '2024-09-01'),
(2, '22233344000155', 2100.00, '2024-10-01');
