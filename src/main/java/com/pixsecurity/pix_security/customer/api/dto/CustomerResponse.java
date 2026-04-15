package com.pixsecurity.pix_security.customer.api.dto;

import com.pixsecurity.pix_security.customer.domain.CustomerStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

// Classe que controla o que será apresentado (neste caso, via GET no POSTMAN)
@Getter
@Setter
@Schema(description = "Dados retornados do cliente")
public class CustomerResponse {

    @Schema(description = "ID único do cliente", example = "3fd965d0-3ea8-4e23-83ec-2a2080f35726")
    private UUID id;

    @Schema(description = "CPF do cliente", example = "12345678901")
    private String cpf;

    @Schema(description = "Nome do cliente", example = "Matheus Botelho")
    private String nome;

    @Schema(description = "E-mail do cliente", example = "matheus@email.com")
    private String email;

    @Schema(description = "Telefone do cliente", example = "11999999999")
    private String telefone;

    @Schema(description = "Status do cliente", example = "ATIVO")
    private CustomerStatus status;

    @Schema(description = "Data de criação do cliente", example = "2026-04-15T10:30:00")
    private LocalDateTime dataCriacao;

    @Schema(description = "Data da última atualização", example = "2026-04-15T11:00:00")
    private LocalDateTime dataAtualizacao;

}

