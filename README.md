# Pix Security API

API REST desenvolvida com Spring Boot para gerenciamento de clientes.

## Funcionalidades

- Cadastro de clientes
- Atualização de dados
- Inativação de clientes
- Validação de dados
- Tratamento global de erros
- Documentação com Swagger

## Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- Bean Validation
- MySQL / H2
- Swagger (OpenAPI)

## Endpoints principais

- POST /api/v1/customers — cria cliente
- PUT /api/v1/customers/{id} — atualiza cliente
- PATCH /api/v1/customers/{id}/deactivate — inativa cliente

## Como executar o projeto

1. Clonar o repositório:

```bash
git clone https://github.com/matheusbmiranda/pix-security.git
```

2. Entrar na pasta do projeto:

```bash
cd pix-security
```

3. Executar a aplicação:

```bash
./mvnw spring-boot:run
```

4. Acessar a documentação:

```
http://localhost:8080/swagger-ui.html
```

## Objetivo

Projeto desenvolvido com foco em boas práticas de APIs REST.

