#!/bin/bash

# Diretório do projeto Spring Boot
PROJECT_DIR="."

# Comando para identificar o processo rodando
PROCESS_NAME="api-0.0.1.jar"

# Parar o processo ativo da aplicação (se estiver rodando)
echo "Parando a aplicação $PROCESS_NAME..."
CURRENT_PID=$(pgrep -f $PROCESS_NAME)

if [ -n "$CURRENT_PID" ]; then
    echo "Matando o processo com PID: $CURRENT_PID"
    kill -9 $CURRENT_PID
else
    echo "Nenhum processo em execução encontrado."
fi

# Atualizar o repositório local
echo "Atualizando o código..."
git pull origin main

# Compilar o projeto
echo "Compilando o projeto..."
mvn clean package -DskipTests

# Iniciar a aplicação em segundo plano
echo "Iniciando a aplicação..."
nohup java -jar target/*.jar > /dev/null 2>&1 &

# Confirmar que a aplicação foi reiniciada
echo "Aplicação $PROCESS_NAME reiniciada com sucesso.
