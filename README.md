# Pix Security API

API REST desenvolvida com Spring Boot para gerenciamento de clientes e validação de segurança em transações PIX.

## Funcionalidades

* Cadastro de clientes
* Atualização de dados
* Inativação de clientes
* Validação de dados
* Tratamento global de erros
* Documentação com Swagger
* Cadastro de vínculos confiáveis (device, wifi, contato PIX)
* Configuração de limite de transação PIX
* Validação de risco de transações (LOW, MEDIUM, HIGH, BLOCKED)
* Auditoria de alterações de limite

## Tecnologias

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Data MongoDB
* Bean Validation
* MySQL
* MongoDB
* Swagger (OpenAPI)
* Docker / Docker Compose
* JUnit + Mockito

## Endpoints principais

### Customer

* POST /api/v1/customers — cria cliente
* PUT /api/v1/customers/{id} — atualiza cliente
* PATCH /api/v1/customers/{id}/deactivate — inativa cliente

### Trusted Bindings

* POST /api/v1/trusted-bindings — cria vínculo confiável
* GET /api/v1/trusted-bindings/{clientId} — lista vínculos

### Pix Limit

* PUT /api/v1/customers/{clientId}/pix-limit — define limite

### Risk Validation

* POST /api/v1/pix/risk/validate — valida risco da transação

## Como executar o projeto

### - Com Docker (recomendado)

```bash
docker-compose up --build
```

A aplicação estará disponível em:
http://localhost:8080

---

### - Sem Docker (modo desenvolvimento)

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

---

## Documentação

Swagger disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

## Testes

Para executar os testes:

```bash
./mvnw.cmd test
```

## Arquitetura

O projeto segue uma arquitetura em camadas:

* **customer** — gerenciamento de clientes
* **trustedbinding** — vínculos confiáveis
* **pixlimit** — limite de transações
* **risk** — orquestração de validação de risco

## Objetivo

Projeto desenvolvido com foco em boas práticas de APIs REST, separação de responsabilidades, testes unitários e integração com múltiplos bancos de dados.

## Status

Sistema completo com:

* Validação de risco
* Integração com MySQL e MongoDB
* Testes automatizados
* Execução via Docker


