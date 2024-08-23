
call insert_cooperativa('85793286000162', 'Cooperativa Sol Nascente', 'solnascente@cooperativa.com', 'Sol@2024#');
call insert_cooperativa('82669102000169', 'Cooperativa Verde Esperança', 'verde.esperanca@coop.com', 'Verde#2023!');
call insert_cooperativa('16122685000189', 'Cooperativa União do Campo', 'uniao.campo@exemplo.com', 'Campo2024#');
call insert_cooperativa('38604265000171', 'Cooperativa AgroMais', 'agromais@coop.com', 'Agro@2023!');

call insert_empresa('AgroTech Solutions', 'contato@agrotech.com', 'Agro#2024!', '11987654321', '12345678000199');
call insert_empresa('NutriVida Alimentos', 'nutrivida@empresa.com', 'Nutri@2024#', '31987654321', '98765432000188');
call insert_empresa('CampoFértil Ltda', 'atendimento@campofertil.com', 'Campo#2023!', '21987654321', '56473829000177');
call insert_empresa('EcoSustentável', 'contato@ecosustentavel.com', 'Eco@2024#', '61987654321', '19283746000166');

call insert_endereco('São Paulo', 'Avenida Paulista', 1234);
call insert_endereco('Belo Horizonte', 'Rua dos Contornos', 567);
call insert_endereco('Porto Alegre', 'Rua Carlos Gomes', 890);
call insert_endereco('Curitiba', 'Avenida Batel', 456);

INSERT INTO produto (tipo_produto, valor_inicial_produto, valor_final_produto, peso, foto_produto)
VALUES
    ('Colheita de soja', 15000.00, 30000.00, 10000, 'soja.jpg'),
    ('Maquinário agrícola', 75000.00, 120000.00, 3500, 'maquinario.jpg'),
    ('Gado leiteiro', 25000.00, 40000.00, 800, 'gado_leiteiro.jpg'),
    ('Insumos agrícolas', 10000.00, 20000.00, 500, 'insumos_agricolas.jpg');

call insert_leilao('2024-09-01', '2024-09-10', 'Leilão de colheita de soja.', '14:00:00', 1, 1);
call insert_leilao('2024-10-01', '2024-10-05', 'Leilão de maquinário agrícola.', '09:00:00', 2, 2);
call insert_leilao('2024-11-15', '2024-11-20', 'Leilão de gado leiteiro.', '15:00:00', 3, 3);
call insert_leilao('2024-12-01', '2024-12-07', 'Leilão de insumos agrícolas.', '10:00:00', 4, 4);

