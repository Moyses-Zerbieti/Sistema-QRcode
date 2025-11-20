#!/bin/bash

echo "Carregando variáveis do .env..."
export $(grep -v '^#' .env | xargs)

echo "Criando network"
docker network create tagid-network 2>/dev/null || echo "Network já existe"

echo "Buildando imagem da aplicação..."
docker build -t moyseszerbieti/tagid .

echo "Subindo container do banco..."
docker run -d \
  --name tagid-db \
  --network tagid-network \
  -e POSTGRES_DB=$POSTGRES_DB \
  -e POSTGRES_USER=$POSTGRES_USER \
  -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD \
  -p 5433:5432 \
  postgres:16.9

echo "Subindo container da aplicação..."
docker run -d \
  --name tagid-prod \
  --network tagid-network \
  -p 8080:8080 \
  -e DATASOURCE_URL=$DATASOURCE_URL \
  -e DATASOURCE_USERNAME=$DATASOURCE_USERNAME \
  -e DATASOURCE_PASSWORD=$DATASOURCE_PASSWORD \
  moyseszerbieti/tagid

echo "Containers rodando!"