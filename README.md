# Projeto gerenciamento de Pessoas

## Visão Geral

O Projeto de Gerenciamento de Pessoas é uma solução que visa otimizar o processo de gerenciamento de recursos humanos. Por meio desta aplicação, os usuarios podem criar e manipular sua conta, como também realizar o cadastro de endereços do mesmo.


## Diagrama de Classes
![diagrama_classes](https://github.com/MuriloVieiraCruz/teste_attus/assets/113257963/0c67d351-2208-4371-ba5e-cb967a9f0660)

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven

# Como executar o projeto

Pré-requisitos: Java 17

## Build Padrão

No Build padrão, basta apenas subir um banco de dados postgres na sua máquina, pode ser feito subindo por um container Docker:

```bash
# Banco Docker
docker run --name postgres-attus_container -e POSTGRES_PASSWORD=post -e POSTGRES_DB=attusdb -p 5434:5432 -d postgres
```

## Build Docker

Caso for de preferência realizar o build pelo Docker, precisa ser realizado os seguintes passos:

```bash
# Alteração da url no application.yml:
url: jdbc:postgresql://db:5432/attusdb

# Executar no terminal do projeto:
.\mvnw clean package

# Na raiz do projeto, executar o comando:
docker compose up -d
```

# Autores

[<img src="https://avatars.githubusercontent.com/u/113257963?v=4" width=115><br><sub>Murilo Vieira Cruz</sub>](https://github.com/MuriloVieiraCruz)
