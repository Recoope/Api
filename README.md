# 🚀 API de Leilões, Lances, Empresas e Cooperativa

Esta é uma API RESTful desenvolvida com **Spring Boot**, que permite gerenciar leilões, lances e empresas em um ambiente cooperativo. A API oferece endpoints para criação e manipulação de lances, gerenciamento de leilões e dados de empresas, bem como a integração com a documentação via **Swagger**.

## 🛠 Funcionalidades

- **Leilão**: Gerenciamento de leilões, permitindo a consulta por ID, material e datas de fim.
- **Lance**: Criação e cancelamento de lances em leilões ativos.
- **Empresa**: Gerenciamento das empresas participantes nos leilões.
- **Cooperativa**: Funções de gerenciamento para cooperativas e suas empresas participantes.

## 📖 Índice

- [Instalação](#instalação)
- [Configuração](#configuração)
- [Documentação](#documentação)
- [Endpoints](#endpoints)
  - [Leilões](#leilões)
  - [Lances](#lances)
  - [Empresas](#empresas)
  - [Cooperativas](#cooperativas)
- [Exemplos de Uso](#exemplos-de-uso)
- [Contribuição](#contribuição)
- [Licença](#licença)

---

## 🛠 Instalação

### Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- [Java 21+](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Git](https://git-scm.com/downloads)

### Passos para Instalação

Execute o seguinte script para atualizar o sistema, instalar as dependências e iniciar a aplicação:

```bash
# Clonando o projeto e fazendo build
git clone https://github.com/Recoope/Api.git

cd ./Api
mvn clean package -DskipTests

# Rodando em background com tmux
cat << EOF > executar.sh
#!/bin/bash
SESSION_NAME=recoopeApiSession

# Definindo variáveis de ambiente
export DB_URL={URL_Banco}
export DB_PORT={Porta_Banco}
export DB_DATABASE={Database_Banco}
export DB_USER={Usuario_Banco}
export DB_PASSWORD={Senha_Banco}

if tmux has-session -t \$SESSION_NAME 2>/dev/null; then
    echo "Session \$SESSION_NAME already exists. Attaching to it."
    tmux attach-session -t \$SESSION_NAME
else
    tmux new-session -d -s \$SESSION_NAME
fi
tmux send-keys -t 0 C-c
tmux send-keys -t 0 'java -jar target/api-0.0.1.jar' C-m
EOF

chmod +x executar.sh
./executar.sh

# Adicionando o script ao crontab para ser executado no reboot
(crontab -l 2>/dev/null; echo "@reboot /home/ubuntu/Api/executar.sh") | crontab -
```

---

## ⚙️ Configuração

### Arquivo de Configuração `application.properties`

Aqui estão as configurações básicas que precisam ser ajustadas no arquivo `application.properties` para conectar ao banco de dados e configurar outras propriedades.

```properties
# Configurações do Banco de Dados
spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
spring.datasource.username=nome_do_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Porta padrão da API
server.port=8080
```

### Banco de Dados

Você pode inicializar o banco de dados utilizando scripts SQL para criar as tabelas de Leilão, Lance, Empresa e Cooperativa.

---

## 📄 Documentação

A API está totalmente documentada com Swagger. Após iniciar o projeto, você pode acessar a documentação interativa no seguinte endereço:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔥 Endpoints

### Leilões

- **GET** `/leilao/{id}`: Busca um leilão pelo seu ID.
- **GET** `/leilao`: Retorna todos os leilões ativados.
- **GET** `/leilao/material/{material}`: Busca leilões por tipo de material.
- **GET** `/leilao/fim`: Busca leilões pela data de fim.
- **GET** `/leilao/vencemNoMes`: Busca leilões que vencem em um mês específico.

### Lances

- **POST** `/lance/{idLeilao}`: Realiza um lance em um leilão ativo.
- **DELETE** `/lance/cancelar`: Cancela um ou mais lances feitos por uma empresa em um leilão.

### Empresas

- **GET** `/empresa/{cnpj}`: Retorna os dados de uma empresa pelo seu CNPJ.
- **POST** `/empresa`: Cadastra uma nova empresa.
- **PUT** `/empresa/{cnpj}`: Atualiza os dados de uma empresa.
- **DELETE** `/empresa/{cnpj}`: Remove uma empresa da base de dados.

### Cooperativas

- **GET** `/cooperativa/{id}`: Retorna os dados de uma cooperativa.
- **POST** `/cooperativa`: Cadastra uma nova cooperativa.
- **PUT** `/cooperativa/{id}`: Atualiza os dados de uma cooperativa.
- **DELETE** `/cooperativa/{id}`: Remove uma cooperativa da base de dados.

---

## 💡 Exemplos de Uso

### Exemplo: Criar um Lance

**Request:**
```bash
POST /lance/1
```

**Body:**
```json
{
  "cnpj": "12345678000100",
  "valor": 1000.00
}
```

**Response:**
```json
{
  "status": "success",
  "message": "Lance atribuído com sucesso!"
}
```

---

## 🤝 Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto.
2. Crie uma nova branch com a sua feature:
   ```bash
   git checkout -b minha-feature
   ```
3. Commit suas mudanças:
   ```bash
   git commit -m 'Adiciona minha nova feature'
   ```
4. Envie sua branch para o repositório remoto:
   ```bash
   git push origin minha-feature
   ```
5. Abra um Pull Request.

---

## 📄 Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).
