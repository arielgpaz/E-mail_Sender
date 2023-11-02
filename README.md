# Remetente de notas escolares por e-mail

---
## Objetivo
Esse projeto foi desenvolvido visando facilitar o dia-a-dia da minha mulher, como representante de turma, ela precisa 
enviar as notas para os colegas, individualmente.
Com esse projeto ela consegue carregar uma planilha .csv, contendo as notas e os e-mails particulares de cada colega
e enviar as notas para cada um deles.

## Tecnologias

- ``Java 17``
- ``Spring Boot 3``
- ``PostgreSql``
- ``Spring Boot Starter Mail``
- ``OpenCSV``
- ``Swagger``

## Funcionalidades

- Carregar um arquivo .csv e enviar as notas contidas nele;
- Baixar um arquivo .csv com o histórico de e-mails enviados;
- Consultar os e-mails enviados filtrando por data, status e destinatário.

## Como usar

Este projeto pode ser usado através do Docker, estando na pasta raiz do projeto basta seguir os passos abaixo:

1) Gerar o arquivo *.jar*: `mvn clean package`
2) Executar o comando: `docker compose up -d`
3) Abrir o swagger: http://localhost:8080/swagger-ui/index.html
4) Para enviar as notas, criar um arquivo .csv com o seguinte modelo:

| Nome   | E-mail    | título 1 | título 2 | ... | título N | Total |
|--------|-----------|----------|----------|-----|----------|-------|
| Ariel  | abc@d.com | 2,5      | 3,0      | ... | 4,5      | 10,0  |
| Fulano | abc@d.com | 2,5      | 3,0      | ... | 4,5      | 10,0  |

## Melhorias a serem feitas
1. Criar um CRUD para cadastrar os alunos;
2. Escrever as informações recebidas pela planinha numa mensageria
(ex.: Kafka, Rabitt MQ) para enviar os e-mails posteriormente.