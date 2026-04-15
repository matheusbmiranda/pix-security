# Pix Security API

API REST desenvolvida com Spring Boot para gerenciamento de clientes e validação de segurança em transações PIX.

---

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
* Auditoria de eventos do cliente

---

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

---

## Endpoints principais

### Customer

* POST /api/v1/customers — cria cliente
* GET /api/v1/customers/{id} — busca cliente
* PUT /api/v1/customers/{id} — atualiza cliente
* PATCH /api/v1/customers/{id}/deactivate — inativa cliente

---

### Trusted Bindings

* POST /api/v1/customers/{clientId}/trusted-bindings — cria vínculo confiável
* GET /api/v1/customers/{clientId}/trusted-bindings — lista vínculos
* DELETE /api/v1/customers/{clientId}/trusted-bindings/{bindingId} — remove vínculo

---

### Pix Limit

* PUT /api/v1/customers/{clientId}/pix-limit — define limite

---

### Risk Validation

* POST /api/v1/pix/risk/validate — valida risco da transação

---

### Audit

* GET /api/v1/customers/{clientId}/audit-history — consulta histórico

---

## Fluxo de uso (exemplo)

1. Criar cliente
2. Criar vínculo confiável
3. Definir limite PIX
4. Validar risco da transação

---

## Como executar o projeto

### Com Docker (recomendado)

```bash
docker-compose up --build
```

A aplicação estará disponível em:

```
http://localhost:8080
```

---

### Sem Docker (modo desenvolvimento)

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
.\mvnw spring-boot:run
```

---

## Documentação

Swagger disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Testes

Para executar os testes:

```bash
.\mvnw test
```

---

## Arquitetura

O projeto segue uma arquitetura em camadas:

* **customer** — gerenciamento de clientes
* **trustedbinding** — vínculos confiáveis
* **pixlimit** — limite de transações
* **risk** — orquestração de validação de risco
* **audit** — registro de eventos do sistema

---

## Objetivo

Projeto desenvolvido com foco em boas práticas de APIs REST, separação de responsabilidades, testes unitários e integração com múltiplos bancos de dados.

---

## Status

Sistema completo com:

* Validação de risco
* Integração com MySQL e MongoDB
* Testes automatizados
* Execução via Docker



